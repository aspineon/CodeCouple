$(function() {
    $("#product_name")
        .autocomplete(
            {
                source : 'http://localhost:8083/autocomplete',
                minLength : 1,
            });
});