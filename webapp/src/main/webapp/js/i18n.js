$(document).ready(function (e) {
    /**
     * By clicking anywhere we close a div.
     *
     */
    $("#languagesChange").click(function (e) {
        e.stopPropagation();
    });
    $("#languagesChange").click(function () {
        if ($("#languagesMenu").is(':visible')) {
            $("#languagesMenu").hide();
        } else {
            $("#languagesMenu").show();
        }
    });
    $(document).click(function () {
        $("#languagesMenu").hide();
    });
});
function updateQueryStringParameter(key, value) {
    /**
     * Updating string query
     */
    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = window.location.href.indexOf('?') !== -1 ? "&" : "?";
    if (window.location.href.match(re)) {
        window.location.href = window.location.href.replace(re, '$1' + key
        + "=" + value + '$2');
    } else {
        window.location.href += separator + key + "=" + value;
    }
}