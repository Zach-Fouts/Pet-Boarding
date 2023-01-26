let deleteDialog;

window.addEventListener('DOMContentLoaded', () => {
    deleteDialog = new bootstrap.Modal('#deleteDialog', {});
});

// Updates the name of the employee's record that is going to be tried to delete,
// also updates the submit route for the controller
// @param name  {string}
// @param id    {number}
function openDeleteDialog(name, id) {
id=500;
    document.getElementById('deleteName').textContent = name;
    deleteForm.action = `/employees/delete/${id}`;
    deleteDialog.show();
}