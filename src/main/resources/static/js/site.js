const SEARCH_URL = '/search?';
const LIST_TEMPLATE_URL = '/list-template';
const REGEX = {
    VALIDATE_PASSWORD: /^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,16}$/,
    STARTS_CODE_BLOCK: /^CODE:/,
    VARIABLE_NAME_CHARS: /^[a-zA-Z0-9_.-]+$/
};
Object.freeze(REGEX);

$(document).ready(function () {

    const SEARCH_INPUT = $("#search-input");
    const SEARCH_SORT = $("#search-sorting");

    $("#search-clear").click(function () {
        SEARCH_INPUT.val('');
        fireSearch();
    });

    // variable for 300ms timeout ID
    var searchTimeout;

    /**
     * Listener for search field. Additionally implemented waiting for preventing flushing to the server.
     * */
    SEARCH_INPUT.keyup(function (evt) {
        // cancel previous waiting if needed, we don't care about current value in javascript
        clearTimeout(searchTimeout);
        // create new waiting and save ID to variable
        searchTimeout = setTimeout(function () {
            searchTimeout = null;
            fireSearch();
        }, 300, evt);
    });

    /**
     * Listener for calling search by sorting change.
     * */
    SEARCH_SORT.change(fireSearch);

    /**
     * Function that send request to the server based on current URL.
     * */
    function fireSearch() {
        // detect current URL and module that will be called
        var module = SEARCH_INPUT.attr("module_name");
        if (module) {
            loadTemplate(module);
            var params = {};
            params.keyword = SEARCH_INPUT.val().trim();
            params.sort = SEARCH_SORT.val();
            var requestUrl = "/" + module + SEARCH_URL + $.param(params);
            $.ajax({url: requestUrl}).done(function (results) {
                // call function that matches to current module
                render(module, results);
            })
        }
    }

    /**
     * Function that check and load if needed template for search for current module.
     * */
    function loadTemplate(module) {
        if (templates[module]) {
            // prevent reloading of template
            return;
        }
        var requestUrl = "/" + module + LIST_TEMPLATE_URL;
        $.ajax({url: requestUrl}).done(function (results) {
            // call function that matches to current module
            var loaded = eval(results);
            // we should remove variable passed to eval(), otherwise it's stays in global memory!
            results = null;
            for (var p in loaded) {
                if (loaded.hasOwnProperty(p)) {
                    templates[p] = loaded[p];
                }
            }
        })
    }

    /**
     * Function that render list for displaying on page.
     *
     * Conventions for render function:
     *  - template will be loaded based on module name and should contain 'header' and 'item' fields
     *  - list items will be injected into header element(as last element), that created for template
     *  - 'empty' template will be used in case if there is no objects and injected into 'header'
     *  - variables be injected from item fields based on name, name should match to: [a-zA-Z0-9_-]
     *  - all variables wrapped in double percent character %%
     *  - automatically can be injected variable 'module_name'
     *  - automatically for list-items can be injected variable 'index', that starts from 0
     *  - can be called inner objects, just use dot to separate levels, example: %%role.simpleName%%
     *  - using 'CODE:' give ability to execute pure javascript code, in the scope available object 'variables',
     *              that contain all variables that will be injected
     *              example: %%CODE: alert("Executed code from template during rendering");%%
     * */
    function render(module, objects) {
        var moduleTemplates = templates[module], index = 0, table = document.createElement("div");

        if (!moduleTemplates) {
            throw "Template for current module (" + module + ") not found!"
        }

        // create list-header
        table.innerHTML = moduleTemplates["header"].replace(/%%module_name%%/g, module);
        table = table.firstElementChild;

        // get object from list
        if (objects && objects.length > 0) {
            for (var o in objects) {
                if (objects.hasOwnProperty(o)) {

                    var result = "", templateParts = moduleTemplates["item"].split("%%");

                    var variables = objects[o];
                    variables["module_name"] = module;
                    variables["index"] = index++;

                    for (var part in templateParts) {
                        if (templateParts.hasOwnProperty(part)) {
                            part = templateParts[part];

                            switch (true) {
                                case REGEX.STARTS_CODE_BLOCK.test(part):
                                    part = part.substring(5);
                                    result += eval(part);
                                    break;
                                case REGEX.VARIABLE_NAME_CHARS.test(part):
                                    // here is known issue of the eval function, the passed argument remains in memory,
                                    // so we should use separate variable and remove this when it's not needed anymore
                                    var tmp = "variables." + part;
                                    tmp = eval(tmp);
                                    result += tmp === undefined ? part : tmp;
                                    tmp = null;
                                    break;
                                default:
                                    result += part;
                                    break;
                            }
                        }
                    }

                    // insert line
                    var line = document.createElement("div");
                    line.innerHTML = result;
                    table.appendChild(line.firstElementChild);
                }
            }
        } else {
            var noResults = document.createElement("div");
            noResults.innerHTML = templates.empty;
            table.appendChild(noResults.firstElementChild);
        }
        $("#" + module + "-list").replaceWith(table);
    }

    /**
     * Templates container for rendering results from the server.
     *
     * All templates, except 'empty', must have 'header' and 'item' fields. Additionally can be implemented something
     * like 'array' template.
     * In all templates should be just one root element.
     * */
    const templates = {
        example: {
            header: '<div>Some HTML that will be as Header</div>',
            item: '<div class="item">' +
            '   <label>%%variable_from_response_name%%</label>' +
            '   <div id="%%index%%-%%module_name%%">' +
            '       %%CODE:console.log("This is executable javascript code part!");%%' +
            '   </div>' +
            '</div>'
        }
    };

    // load template for current module
    if (SEARCH_INPUT.length) {
        // detect current URL and module that will be called
        var module = window.location.pathname.split("/")[1];
        SEARCH_INPUT.attr("module_name", module);
        loadTemplate(module);
    }

    $('#fragment').on('click', ".see-more", function (event) {
        var text = $(this).text();
        text = text === 'See more' ? 'See less' : 'See m' +
            'ore';
        $(this).text(text);
        var more = '#more-' + event.target.id;
        $(more).toggle();
    });

    if ($('.datepicker').length) {
        $('.datepicker').datetimepicker({
            format: 'MM/DD/YYYY HH:mm',
            ignoreReadonly: true
        });
    }

    var firstPassword = document.getElementById("first-password");
    var secondPassword = document.getElementById("password");

    if (firstPassword && secondPassword) {
        $(firstPassword).on("input", function () {
            var val = $(this).val();
            firstPassword.style.backgroundColor = val.match(REGEX.VALIDATE_PASSWORD) ? "chartreuse" : "indianred";
        });

        $(secondPassword).on("input", function () {
            secondPassword.style.backgroundColor = $(this).val() === $(firstPassword).val() ? "chartreuse" : "indianred";
        })
    }

    $("#telephone").mask("000-000-0000")

    $('.integer').mask('0000000000');
});