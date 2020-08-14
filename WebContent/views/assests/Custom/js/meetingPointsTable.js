$(document).ready(function () {
  var id = 2;
  $('.addRow').click(() => {
    id = id + 1;
    var options = '';
    optionsArray.forEach((item, index) => {
    	options += '<option value="' + item + '">' + item + '</option>';
    });
    var newEntry = '<tr class="meetingPoint">' +
      '<td><input type="checkbox" name="delete" class="checkbox form-control" />' + 
      '<input type="text" name="meetingPointId" hidden class="form-control" value="0" required /></td>' +
      '<td colspan="4">' +
      '<input type="text" name="meetingPointTasks" class="form-control" required />' +
      '</td>' +
      '<td class="justify-contents-center">' +
      '<select class="actionItem form-control" name="actionItem"><option value="NO">NO</option><option value="YES">YES</option></select>' +
      '</td>' +
      '<td>' + '<select class="assignee form-control" name="assignee"><option>Not Assigned</option>'
      + '<c:forEach items="${mpOptions }" var="option">'
      + options
      + '</c:forEach></select>' +
      '</td>' +
      '</tr>';
    $('.meetingTableBody').prepend(newEntry);
  });

  $(".deleteRow").click(function () {
    $('table tbody').find('input[name="delete"]').each(function () {
      if ($(this).is(':checked')) {
        $(this).parents("tr").remove();
      }
    })
  });
});