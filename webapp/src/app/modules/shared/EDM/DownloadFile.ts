export function downloadFile(response, fileName) {
    var fileURL = null;
    if (response.size != 0) {
    fileURL = window.URL.createObjectURL(new Blob([response], {type: ''}));
    //this is for file download :)
    var fileLink = document.createElement('a');
    fileLink.href = fileURL;
    // fileName in this form : filename.extension
    fileLink.setAttribute('download', fileName);
    document.body.appendChild(fileLink);
    fileLink.click();
    return fileURL
    }
}
