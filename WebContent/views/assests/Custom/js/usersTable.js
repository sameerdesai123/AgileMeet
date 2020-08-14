$(document).ready(function () {
  var id = 2;
  $('.addRow').click(() => {
    id = id + 1;
    var options = '';
    optionsArray.forEach((item, index) => {
    	options += '<option value="' + item + '">' + item + '</option>';
    });
    var newEntry = '<tr class="user">' +
      '<td><input type="checkbox" name="delete" class="checkbox form-control" />' + 
      '<input type="text" name="id" hidden class="form-control" value="0" required /></td>' +
      '<td colspan="4">' +
      '<input type="text" name="name" class="form-control" required />' +
      '</td>' +
      '<td class="justify-contents-center">' +
      '<input type="email" name="email" class="form-control" required />' +
      '</td>' +
      '</tr>';
    $('.usersTableBody').prepend(newEntry);
  });

  $(".deleteRow").click(function () {
    $('table tbody').find('input[name="delete"]').each(function () {
      if ($(this).is(':checked')) {
        $(this).parents("tr").remove();
      }
    })
  });
});