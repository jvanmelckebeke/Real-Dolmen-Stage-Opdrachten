package org.jarivm.relationGraph.controllers;

import org.jarivm.relationGraph.domains.Client;
import org.jarivm.relationGraph.domains.Employee;
import org.jarivm.relationGraph.domains.Project;
import org.jarivm.relationGraph.domains.Sector;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;

/**
 * @author Jari Van Melckebeke
 * @since 24.10.16.
 */
@Controller
@RequestMapping("/user/search")
public class SearchController extends BaseController {

	@RequestMapping("/simpleSearch")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_PROJECT_LEADER', 'ROLE_CLIENT')")
	public String simpleSearch(@RequestParam(name = "q") String query, @RequestParam(name = "t") String type, Model model) {
		if (type.startsWith("Client,")) {
			String prop = type.substring(7);
			System.out.println(prop);
			Iterable<Client> clients = clientRepository.findByProperty(prop, query);
			model.addAttribute("results", clients);
			model.addAttribute("type", "client");
			return "/user/searchResult";
		}
		if (type.startsWith("Employee,")) {
			String prop = type.substring(9);
			System.out.println(prop);
			Iterable<Employee> employees = employeeRepository.findByProperty(prop, query);
			model.addAttribute("results", employees);
			model.addAttribute("type", "employee");
			return "/user/searchResult";
		}
		if (type.startsWith("Project,")) {
			String prop = type.substring(8);
			System.out.println(prop);
			Iterable<Project> results = projectRepository.findByProperty(prop, query);
			model.addAttribute("results", results);
			model.addAttribute("type", "project");
			return "/user/searchResult";
		}
		if (type.startsWith("Sector,")) {
			String prop = type.substring(7);
			System.out.println(prop);
			Iterable<Sector> sectors = sectorRepository.findByProperty(prop, query);
			model.addAttribute("results", sectors);
			model.addAttribute("type", "sector");
			return "/user/searchResult";
		}
		return "/error/404";
	}
}
