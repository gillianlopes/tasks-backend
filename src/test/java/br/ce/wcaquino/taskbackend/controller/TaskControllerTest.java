package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@InjectMocks 
	TaskController taskControllerMock;
	
	@Mock
	TaskRepo taskRepoMock;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveSalvarTarefaSemDescricao() throws ValidationException {
	
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		
		taskControllerMock.save(task);

	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveSalvarTarefaSemData() throws ValidationException {
		
		Task task = new Task();
		task.setTask("teste");
		
		taskControllerMock.save(task);
		
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveSalvarTarefaComDataPassada() throws ValidationException {
		
		Task task = new Task();
		task.setTask("teste");
		task.setDueDate(LocalDate.of(2010, 01, 01));
		
		taskControllerMock.save(task);
		
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		
		Task task = new Task();
		task.setTask("teste");
		task.setDueDate(LocalDate.now());
		
		Mockito.when(taskRepoMock.save(Mockito.any())).thenReturn(task);
		
		ResponseEntity<Task> retorno = taskControllerMock.save(task);
		assertThat(retorno.getBody().getTask(), CoreMatchers.is("teste"));
		
	}

}
