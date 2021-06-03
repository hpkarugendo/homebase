var problem = document.getElementById("display-text-value").value;
var usedDot = false;

function readValue(value) {
	if (isOp(value)) {
		if (problem.trim().length == 0)  {
			if (value == "+" || value == "-") {
				problem = problem + value;
			}
		} else {
			if (isOp(problem.charAt(problem.length - 1))) {
				problem = problem.substring(0, problem.length - 1) + value;
			} else {
				if(value == "."){
					if(!usedDot){
						problem = problem + value;
						usedDot = true;
					}
				} else {
					problem = problem + value;
					usedDot = false;
				}
			}
		}
	} else {
		problem = problem + value;
	}
	document.getElementById("display-text-value").value = problem;
}

function isOp(cha) {
	var ans = false;

	if (cha == "/" || cha == "*" || cha == "-" || cha == "+" || cha == ".") {
		ans = true;
	}

	return ans;
}

function clearValue() {
	problem = problem.substring(0, problem.length - 1);
	document.getElementById("display-text-value").value = problem;
}