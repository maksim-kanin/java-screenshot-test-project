<#assign baseUrl="https://api.github.com">
<script>
    document.getElementById("${id}-slider").onchange = function (e) {
        let sliderValue = e.target.value;
        document.getElementById("${id}")
            .getElementsByClassName("carousel-item active")[0].style.transform = 'scale(' + sliderValue + ')';
    }

    function checkCredentials() {
        let token = localStorage.getItem("gitHubToken")
        let updateButton = document.getElementById("${id}-update")
        if (token == null) {
            updateButton.setAttribute("disabled", "")
            updateButton.setAttribute("data-mdb-toggle", "tooltip")
            updateButton.setAttribute("data-mdb-placement", "bottom")
            updateButton.setAttribute("title", "Please, specify GitHub token!")
            updateButton.innerHTML = "Disabled";
        }
    }

    async function save() {
        let branchName = document.getElementById("${id}-branch-input").value
        let referencePath = "${path}?ref=" + branchName
        let url = "${baseUrl}/repos/${owner}/${repo}/contents/" + referencePath
        let body = {
            "branch": branchName,
            "committer": {
                "name": localStorage.getItem("gitHubUser"),
                "email": localStorage.getItem("gitHubEmail")
            },
            "content": document.getElementById("${id}-actual").src.replace("data:image/png;base64,", "")
        };
        let refResponse = checkReference(url)
        if (refResponse.ok) {
            body.message = "Update screenshot " + referencePath
            body.sha = await response.json().sha;
            await post(url, body)
        } else {
            body.message = "Save screenshot " + referencePath
            await post(url, body)
        }
    }

    async function checkReference(url) {
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Accept": "application/vnd.github.v3+json",
                "Authorization": "Bearer " + localStorage.getItem("gitHubToken")
            }
        });
    }

    async function post(url, body) {
        let response = await fetch(url, {
            method: 'PUT',
            headers: {
                "Accept": "application/vnd.github.v3+json",
                "Authorization": "Bearer " + localStorage.getItem("gitHubToken")
            },
            body: JSON.stringify(body)
        });
        if (response.ok) {
            $("#${id}-modal").modal('hide')
            $("#${id}-modal-success").modal('show')
        } else {
            alert("Error: " + response.status);
        }
    }
</script>