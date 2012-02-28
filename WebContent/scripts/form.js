function javascriptTest(){
	alert('Testing');
	return false;
}

function validateForm(){
	// alert('Testing');
	// objForm = document.getElementsByTagName('form');
	var bValid = true;
	var bNotInvalid = true;
	if (document.getElementsByTagName)
	{
		var objField = document.getElementsByTagName('*');
		for (var iFieldCounter=0; iFieldCounter<objField.length; iFieldCounter++){
			bValid = true;
			arClass = objField[iFieldCounter].className.split(' ');
			for(var iClassCounter=0; iClassCounter<arClass.length && bValid == true; iClassCounter++){
				switch(arClass[iClassCounter]){
					case 'required':
						bValid = isRequired(objField[iFieldCounter]);
						break;
					case 'email':
						bValid = isValidEmail(objField[iFieldCounter]);
						break;
					default:
						bValid = true;
				}
			}
			if (bValid == false){
				bNotInvalid = false;
			}
		}
	}
	
	// issue with submit?
	//document.getElementsByTagName('form').submit();
	
	return bNotInvalid;
}

function isRequired(field){
	removeInvalidMessage(field);
	var value = field.value;
	if(value == ''){
		addInvalidMessage(field, 'Field is empty');
		return false;
	}
	return true;
}

function isValidEmail(field){
	removeInvalidMessage(field);
	var emailRegEx = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	
	if(field.value != '' && emailRegEx.test(field.value) == false){
		addInvalidMessage(field, 'Invalid email address');
		return false;
	}
	return true;
}

function addInvalidMessage(field, message){
	var errorMessage = document.createTextNode(message);
	field.parentNode.insertBefore(errorMessage,field.nextSibling);
}

function removeInvalidMessage(field){
	var nextField = field.nextSibling;
	if(nextField != null && nextField.nodeName == '#text'){
		field.parentNode.removeChild(nextField);
	}
}

function cloneElement(elementToClone, minOccurs, maxOccurs)
{
	var numElements = 0;
	var nodeList;

	nodeList = elementToClone.parentNode.getElementsByTagName("div");
	for ( var iNode = 0; iNode < nodeList.length; iNode++)
	{
		// If the node has a 'name' attribute with a value same as the parentNode's name attribute then increase numElements counter by one.
		if (nodeList.item(iNode).attributes.getNamedItem("name") != null
				&& nodeList.item(iNode).attributes.getNamedItem("name").value == elementToClone.getAttribute("name"))
			numElements++;
	}

	// If max number of elements has already been reached, don't add a new instance of the element.
	if (numElements < maxOccurs)
	{
		var nextSiblingElement = getNextSiblingElement(elementToClone);

		// Find the next node which is an Element.
		if (nextSiblingElement == null)
			elementToClone.parentNode.appendChild(elementToClone.cloneNode(10));
		else
			elementToClone.parentNode.insertBefore(elementToClone.cloneNode(10), nextSiblingElement);
	}

	return;
}

function removeElement(elementToRemove, minOccurs, maxOccurs)
{
	var numElements = 0;

	//elementToRemove.
	
	/*nodeList = elementToRemove.parentNode.getElementsByTagName("div");
	for ( var iNode = 0; iNode < nodeList.length; iNode++)
	{
		// If the node has a 'name' attribute with a value same as the parentNode's name attribute then increase numElements counter by one.
		if (nodeList.item(iNode).attributes.getNamedItem("name") != null
				&& nodeList.item(iNode).attributes.getNamedItem("name").value == elementToRemove.getAttribute("name"))
			numElements++;
	}

	if (numElements > minOccurs)
	{*/
		elementToRemove.parentNode.removeChild(elementToRemove);
	//}

	return;
}

function getNextSiblingElement(curNode)
{
	var nextSiblingElement = curNode.nextSibling;

	// Find the next node which is an Element.
	while (nextSiblingElement != null && nextSiblingElement.nodeType != 1)
	{
		nextSiblingElement = nextSiblingElement.nextSibling;
	}

	return nextSiblingElement;
}

function getNextTableRow(curNode)
{
	var nextRowElement = curNode.nextSibling.nextSibling.childNodes[1].childNodes[1].nextSibling;
	
	return nextRowElement;
}

