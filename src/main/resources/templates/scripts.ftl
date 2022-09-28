<#assign baseUrl="https://api.github.com">
<script>
    function checkUpdates() {
        let screenshot = localStorage.getItem("${id}-actual");
        let token = localStorage.getItem("gitHubToken");
        let updateButton = document.getElementById("${id}-update");
        if (token == null) {
            updateButton.setAttribute("disabled", "");
            updateButton.innerHTML = "Fill GitHub tab";
            updateButton.classList.add("bg-light");
        }
        if (screenshot === "updated") {
            updateButton.setAttribute("disabled", "");
            updateButton.innerHTML = "Updated!";
            updateButton.classList.add("bg-light");
        }
    }

    checkUpdates()

    async function save() {
        let branchName = document.getElementById("${id}-branch-input").value;
        let referencePath = "${path}?ref=" + branchName;
        let url = "${baseUrl}/repos/${owner}/${repo}/contents/" + referencePath;
        let postUrl = "${baseUrl}/repos/${owner}/${repo}/contents/${path}";
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
            body.message = "Update screenshot ${path}";
            body.sha = json.sha;
            await post(postUrl, body);
        } else {
            body.message = "Save screenshot ${path}";
            await post(postUrl, body);
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
            $("#${id}-modal").modal('hide');
            $("#${id}-modal-success").modal('show');
        } else {
            alert("Error: " + response.status);
        }
        localStorage.setItem("${id}-actual", "updated");
    }

    function showActualIgnoring() {
        showIgnoredAreasFor("${id}-actual")
    }

    function showReferenceIgnoring() {
        showIgnoredAreasFor("${id}-reference")
    }

    function showDiffIgnoring() {
        showIgnoredAreasFor("${id}-diff")
    }

    function showIgnoredAreasFor(imageId) {
        let canvases = document.getElementsByTagName("canvas");
        if (canvases.length > 0) {
            for (let i = 0; i < canvases.length; i++) {
                let canvas = canvases[i];
                canvas.parentNode.removeChild(canvas);
            }
            return
        }
        const ignoredAreas = ${ignoredAreas};
        let img = document.getElementById(imageId);
        let rect = img.getBoundingClientRect();
        let sizingX = img.clientWidth / img.naturalWidth;
        let sizingY = img.clientHeight / img.naturalHeight;

        for (let i = 0; i < ignoredAreas.length; i++) {
            let realX = sizingX * ignoredAreas[i].x;
            let realY = sizingY * ignoredAreas[i].y;
            let realWidth = sizingX * ignoredAreas[i].width;
            let realHeight = sizingY * ignoredAreas[i].height;

            let canvas = document.createElement("canvas");
            canvas.setAttribute("width", realWidth);
            canvas.setAttribute("height", realHeight);
            canvas.setAttribute("style",
                'position: absolute; top: ' + (rect.top + realY) + 'px; left: ' + (rect.left + realX) + 'px; z-index:15000');
            document.body.appendChild(canvas);
            let ctx = canvas.getContext("2d");
            ctx.fillRect(0, 0, 151, 100);
        }
    }

    function scaleActual() {
        $("#${id}-actual").scale();
    }

    function scaleDiff() {
        $("#${id}-diff").scale();
    }

    function scaleReference() {
        $("#${id}-reference").scale();
    }
</script>