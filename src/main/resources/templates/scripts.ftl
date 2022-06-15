<#assign baseUrl="https://api.github.com">
<script>
    function checkUpdates() {
        let screenshot = localStorage.getItem("${id}-actual")
        let token = localStorage.getItem("gitHubToken")
        let updateButton = document.getElementById("${id}-update")
        if (token == null) {
            updateButton.setAttribute("disabled", "")
            updateButton.innerHTML = "Fill GitHub tab"
            updateButton.classList.add("bg-light")
        }
        if (screenshot === "updated") {
            updateButton.setAttribute("disabled", "")
            updateButton.innerHTML = "Updated!"
            updateButton.classList.add("bg-light")
        }
    }

    checkUpdates()

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
        let refResponse = await fetch(url, {
            method: 'GET',
            headers: {
                "Accept": "application/vnd.github.v3+json",
                "Authorization": "Bearer " + localStorage.getItem("gitHubToken")
            }
        });
        if (refResponse.ok) {
            let json = await refResponse.json();
            body.message = "Update screenshot " + referencePath
            body.sha = json.sha;
            await post(url, body)
        } else {
            body.message = "Save screenshot " + referencePath
            await post(url, body)
        }
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
        localStorage.setItem("${id}-actual", "updated")
    }

    function scaleActual() {
        $("#${id}-actual").scale()
    }

    function scaleDiff() {
        $("#${id}-diff").scale()
    }

    function scaleReference() {
        $("#${id}-reference").scale()
    }
</script>