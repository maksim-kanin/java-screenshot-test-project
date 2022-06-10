'use strict'

var GitHubModel = Backbone.Model.extend({
    url: 'data/behaviors.json'
});

class GitHubLayout extends allure.components.AppLayout {

    initialize() {
        this.model = new GitHubModel();
    }

    loadData() {
        return this.model.fetch();
    }

    getContentView() {
        return new GitHubView({items: this.model.models});
    }
}

const template = function (data) {
    return '<html lang="en" style="height: 100%">\n' +
        '<head>\n' +
        '    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>\n' +
        '    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>\n' +
        '    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.1.0/mdb.min.css" rel="stylesheet"/>\n' +
        '    <style>\n' +
        '        form {\n' +
        '            width: 20%;\n' +
        '        }\n' +
        '    </style>\n' +
        '</head>\n' +
        '<body style="height: 100%;">\n' +
        '<div id="GitHubContainer" class="container-fluid bg-light d-flex justify-content-center align-items-center"\n' +
        '     style="height: 100%;">\n' +
        '    <form id="GitHubForm">\n' +
        '        <div class="form-outline mb-4">\n' +
        '            <input type="text" id="GitHubUser" class="form-control"/>\n' +
        '            <label class="form-label" for="GitHubUser">User</label>\n' +
        '        </div>\n' +
        '        <div class="form-outline mb-4">\n' +
        '            <input type="text" id="GitHubEmail" class="form-control"/>\n' +
        '            <label class="form-label" for="GitHubEmail">E-mail</label>\n' +
        '        </div>\n' +
        '        <div class="form-outline mb-4">\n' +
        '            <input type="password" id="GitHubToken" class="form-control"/>\n' +
        '            <label class="form-label" for="GitHubToken">Token</label>\n' +
        '        </div>\n' +
        '        <div class="row justify-content-center">\n' +
        '            <button type="button" id="GitHubLogin" class="btn btn-success btn-rounded"\n' +
        '                    data-mdb-ripple-color="dark" onClick="return login()"\n' +
        '                    style="width: 40%">Login\n' +
        '            </button>\n' +
        '        </div>\n' +
        '    </form>\n' +
        '    <div id="GitHubLogout" class="row justify-content-center d-none">\n' +
        '        <button type="button" class="btn btn-outline-danger btn-rounded" data-mdb-ripple-color="dark"\n' +
        '                onClick="logout()">\n' +
        '            Logout\n' +
        '        </button>\n' +
        '    </div>\n' +
        '</div>\n' +
        '<script>\n' +
        '    function login() {\n' +
        '        let user = document.getElementById("GitHubUser").value\n' +
        '        let email = document.getElementById("GitHubEmail").value\n' +
        '        let token = document.getElementById("GitHubToken").value\n' +
        '        if (token.trim() === "") {\n' +
        '            alert("Token must not be empty!");\n' +
        '            return false;\n' +
        '        }\n' +
        '        document.getElementById("GitHubForm").classList.add("d-none")\n' +
        '        document.getElementById("GitHubLogout").classList.remove("d-none")\n' +
        '        populateLocalStorage(user, email, token)\n' +
        '    }\n' +
        '\n' +
        '    function logout() {\n' +
        '        document.getElementById("GitHubForm").classList.remove("d-none")\n' +
        '        document.getElementById("GitHubLogout").classList.add("d-none")\n' +
        '        localStorage.clear()\n' +
        '    }\n' +
        '\n' +
        '    function populateLocalStorage(user, email, token) {\n' +
        '        localStorage.setItem("gitHubUser", user)\n' +
        '        localStorage.setItem("gitHubEmail", email)\n' +
        '        localStorage.setItem("gitHubToken", token)\n' +
        '    }\n' +
        '\n' +
        '    jQuery("#GitHubContainer").on(\'DOMSubtreeModified\', function () {\n' +
        '        let token = localStorage.getItem("gitHubToken")\n' +
        '        if (token !== null) {\n' +
        '            document.getElementById("GitHubForm").classList.add("d-none")\n' +
        '            document.getElementById("GitHubLogout").classList.remove("d-none")\n' +
        '        }\n' +
        '    });\n' +
        '</script>\n' +
        '<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.1.0/mdb.min.js"></script>\n' +
        '</body>\n' +
        '</html>';
}

var GitHubView = Backbone.Marionette.View.extend({
    template: template,

    render: function () {
        this.$el.html(this.template(this.options));
        return this;
    }
})

allure.api.addTab('github', {
    title: 'GitHub',
    icon: 'github-logo',
    route: 'github',
    onEnter: (function () {
        return new GitHubLayout()
    })
});