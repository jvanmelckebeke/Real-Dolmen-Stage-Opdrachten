package org.jarivm.relationGraph.controllers;

import org.jarivm.relationGraph.domains.Employee;
import org.jarivm.relationGraph.domains.Issued;
import org.jarivm.relationGraph.domains.Project;
import org.jarivm.relationGraph.domains.WorkedOn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jari Van Melckebeke
 * @since 17.10.16.
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

	@RequestMapping("/")
	public String root(Model model) {
		return index(model);
	}

	@RequestMapping("/index")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(projectRepository.findByClientName(authentication.getName()));
		model.addAttribute("userprojects", projectRepository.findByClientName(authentication.getName()));
		return "/user/index";
	}

	@RequestMapping(value = "/tableOverview")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_LEADER')")
	public String graph(@RequestParam(name = "limit", defaultValue = "150", required = false) Long limit, Model model) {
		System.out.println(limit);
		model.addAttribute("graphClient", clientRepository.graph(limit));
		model.addAttribute("graphProject", projectRepository.graph(limit));
		return "/user/tableOverview";
	}

	@RequestMapping(value = "/employeeByScore")
	public String employeeByScore(@RequestParam(name = "limit", defaultValue = "150", required = false) Long limit, Model model) {
		model.addAttribute("graphEmployee", employeeRepository.employeesOfAllTime(limit));
		return "/user/employeeByScore";
	}

	@RequestMapping("/viewClient/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER')")
	public String viewClient(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("client", clientRepository.findById(id));
		return "/view/client";
	}

	@RequestMapping("/viewEmployee/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER')")
	public String viewEmployee(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("employee", employeeRepository.findById(id));
		return "/view/employee";
	}

	@RequestMapping("/viewProject/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER') or (hasRole('ROLE_CLIENT') and @baseController.getHasRelationWithProject(#id))")
	public String viewProject(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("project", projectRepository.findById(id));
		return "/view/project";
	}

	@RequestMapping("/viewSector/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER')")
	public String viewSector(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("sector", sectorRepository.findById(id));
		return "/view/sector";
	}

	@RequestMapping(value = "/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteNode(@PathVariable(name = "id") Long id, Model model) {
		switch (getTypeOfNode(id)) {
			case CLIENT_TYPE:
				clientRepository.delete(id);
				return "redirect:/user/index.html";
			case EMPLOYEE_TYPE:
				employeeRepository.delete(id);
				return "redirect:/user/index.html";
			case PROJECT_TYPE:
				projectRepository.delete(id);
				return "redirect:/user/index.html";
			case SECTOR_TYPE:
				sectorRepository.delete(id);
				return "redirect:/user/index.html";
			default:
				return "/error/404";
		}
	}

	@RequestMapping(value = "/evaluate/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER') or (hasRole('ROLE_CLIENT') and @baseController.getHasRelationWithProject(#id))")
	public String evaluate(@PathVariable("id") Long id, Model model) {
		Project p = projectRepository.findById(id);
		System.out.println("p = " + p);
		model.addAttribute("project", p);
		List<WorkedOn> workedOns = p.getWorkedOn();
		Collections.sort(workedOns, (t, t1) -> (int) (t.getEmployee().getId() - t1.getEmployee().getId()));
		model.addAttribute("workedOns", workedOns.iterator());
		List<Employee> employeesCollaborated = p.getWorkedOn().stream().map(WorkedOn::getEmployee).collect(Collectors.toList());
		model.addAttribute("employeesCollaborated", employeesCollaborated);
		return "/user/evaluate";
	}

	@RequestMapping(value = "/evaluate/{id}/confirm")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER') or (hasRole('ROLE_CLIENT') and @baseController.getHasRelationWithProject(#id))")
	public String confirmEvaluate(@PathVariable("id") Long id,
								  @RequestParam("issuedId") Long issuedId,
								  @RequestParam("score") List<Double> score,
								  @RequestParam("workedOnId") List<Long> workedOnId,
								  @RequestParam("cost") Float cost,
								  Model model) {
		List<WorkedOn> workedOns = new ArrayList<>();
		for (int i = 0; i < workedOnId.size(); i++) {
			WorkedOn w = workedOnRepository.findById(workedOnId.get(i));
			w.setScore(score.get(i));
			workedOns.add(w);
		}
		List<Issued> issuedList = new ArrayList<>();
		issuedList.add(issuedRepository.findById(issuedId));
		issuedRepository.save(issuedList);
		workedOnRepository.save(workedOns);
		Project p = projectRepository.findById(id);
		p.setCost(cost);
		projectRepository.save(p);
		return "redirect:/user/index.html";
	}
}
