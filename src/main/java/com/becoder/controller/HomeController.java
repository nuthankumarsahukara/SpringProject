package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.becoder.entity.Employee;
import com.becoder.service.EmpService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private EmpService empService;

	@GetMapping("/login")
	    public String loginPage() {
		return "login";
	    }
	
	    @PostMapping("/login")
	    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		// Add authentication logic here
		// For simplicity, you can check hardcoded credentials
		if ("nuthan".equals(username) && "nuthan".equals(password)) {
		    session.setAttribute("username", username);
		    return "redirect:/";
		} else {
		    session.setAttribute("msg", "");
		    return "redirect:/login";
		}
	    }
	    
	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
		// Invalidate the session
		session.invalidate();
		return "redirect:/login";
	    }

	@GetMapping("/")
	public String index(Model m, HttpSession session) {
	 	if (session.getAttribute("username") == null) {
			// Redirect to login page if not authenticated
			return "redirect:/login";
	    	}
		List<Employee> list = empService.getAllEmp();
		m.addAttribute("empList", list);
		return "index";
	}

	@GetMapping("/loadEmpSave")
	public String loadEmpSave() {
		return "emp_save";
	}

	@GetMapping("/EditEmp/{id}")
	public String EditEmp(@PathVariable int id, Model m) {
		// System.out.println(id);
		Employee emp = empService.getEmpById(id);
		m.addAttribute("emp", emp);
		return "edit_emp";
	}

	@PostMapping("/saveEmp")
	public String saveEmp(@ModelAttribute Employee emp, HttpSession session) {
		// System.out.println(emp);

		Employee newEmp = empService.saveEmp(emp);

		if (newEmp != null) {
			// System.out.println("save success");
			session.setAttribute("msg", "Register sucessfully");
		} else {
			// System.out.println("something wrong on server");
			session.setAttribute("msg", "something wrong on server");
		}

		return "redirect:/loadEmpSave";
	}

	@PostMapping("/SpringProject/updateEmpDtls")
	public String updateEmp(@ModelAttribute Employee emp, HttpSession session) {
		// System.out.println(emp);

		Employee updateEmp = empService.saveEmp(emp);

		if (updateEmp != null) {
			// System.out.println("save success");
			session.setAttribute("msg", "Update sucessfully");
		} else {
			// System.out.println("something wrong on server");
			session.setAttribute("msg", "something wrong on server");
		}

		return "redirect:/";
	}

	@GetMapping("/deleteEmp/{id}")
	public String loadEmpSave(@PathVariable int id, HttpSession session) {
		boolean f = empService.deleteEmp(id);
		if (f) {
			session.setAttribute("msg", "Delete sucessfully");
		} else {
			session.setAttribute("msg", "something wrong on server");
		}
		return "redirect:/";
	}

}
