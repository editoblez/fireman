function handleDialogSubmit(xhr, status, args, dialog) {
	if (args.validationFailed) {
		dialog.show();
	} else {
		dialog.hide();
	}
}