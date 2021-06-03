package com.hpkarugendo.projects.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CalculatorController {
	
	@GetMapping("projects/calculator")
	public String index(Model m) {
		if(!m.containsAttribute("calObject")) {
			m.addAttribute("calObject", new CalculatorModel());
		}
		
		return "projects/calculator/index";
	}
	
	@PostMapping("projects/calculator/problem")
	public String problem(CalculatorModel calObject, RedirectAttributes ra) {
		CalculatorModel temp = calObject;
		temp.setProblem(temp.getSolution());
		CalculatorLogic logic = new CalculatorLogic(temp);
		if(!logic.isValid()) {
			temp.setMsg("ERROR - Bad Equation!");
		} else if(logic.isZeroDivision()) {
			temp.setMsg("ERROR - Can't Divide By Zero!");
		} else {
			temp = logic.calculate();
		}
		
		
		ra.addFlashAttribute("calObject", temp);
		
		return "redirect:/projects/calculator";
	}

}
