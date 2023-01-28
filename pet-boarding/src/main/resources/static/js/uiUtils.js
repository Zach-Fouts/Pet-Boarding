let deleteDialog;

// Updates the name of the employee's record that is going to be tried to delete,
// also updates the submit route for the controller
// @param name  {string}
// @param id    {number}
function openDeleteDialog(name, id, path = '/employees') {
    addDeleteDialogElement();
    document.getElementById('deleteName').textContent = name;
    deleteForm.action = `${path}/delete/${id}`;
    deleteDialog.show();
}

// Checks for the existence of the boostraps.Modal object deleteDialog, if it doesn't
// exists check if the HTML Elements exist, if it doesn't exist adds it to the content and
// finally creates the object required to use the modal.
function addDeleteDialogElement( ) {
    if(deleteDialog) return;
    if(!document.querySelector('#deleteDialog')) {
        let dialogFragment = document.createRange().createContextualFragment(`
        <div id="deleteDialog" class="modal" tabindex="-1">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Delete employee</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>Do you want to delete <span id="deleteName" class="fw-semibold"></span>'s entry?. To continue press delete.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <form id="deleteForm" method="post" action="#">
                                    <button id="btnDeleteEmployee" type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
        `);
        document.querySelector('main.content').appendChild(dialogFragment);
    }
    deleteDialog = new bootstrap.Modal('#deleteDialog', {});
}