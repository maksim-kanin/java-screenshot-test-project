<div class="modal fade" id="${id}-modal" tabindex="-1" aria-labelledby="${id}-modal-label" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="${id}-modal-label">Save screenshot to branch?</h5>
                <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="text" id="${id}-branch-input" class="form-control"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary rounded-pill" data-mdb-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary rounded-pill" onclick="save()">Save changes</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="${id}-modal-success">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title h4">Successfully updated!</h5>
                <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
            </div>
        </div>
    </div>
</div>