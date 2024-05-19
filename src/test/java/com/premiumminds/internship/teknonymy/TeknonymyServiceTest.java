package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(expected, result);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1056, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(expected, result);
  }
	
	@Test
	public void InvalidPersonTest() {
		String result = new TeknonymyService().getTeknonymy(null);
		String expected = null;
		assertEquals(expected, result);
	}

	@Test
  public void PersonInvalidChildTest() {
    Person person = new Person("John", 'M', new Person[]{ null }, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(expected, result);
  }

  @Test
  public void PersonOneChildDifferentSexTest() {
    Person person = new Person(
        "Caroline",
        'F',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1056, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "mother of Holy";
    assertEquals(expected, result);
  }

  @Test
  public void PersonTwoChildrenTest() {
		Person child1 = new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0));
		Person child2 = new Person("Mary", 'F', null, LocalDateTime.of(1045, 1, 1, 0, 0));

		Person person = new Person("Caroline", 'F', new Person[]{ child1, child2 }, LocalDateTime.of(1020, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "mother of Mary";
		assertEquals(expected, result);
  }

  @Test
  public void PersonGrandchildrenTest() {
		Person grandchild1 = new Person("Teresa", 'F', null, LocalDateTime.of(1065, 1, 1, 0, 0));
		Person grandchild2 = new Person("William", 'M', null, LocalDateTime.of(1068, 1, 1, 0, 0));
		
		Person child1 = new Person("Holy", 'F', new Person[]{ grandchild1 }, LocalDateTime.of(1046, 1, 1, 0, 0));
		Person child2 = new Person("Mary", 'F', new Person[]{ grandchild2 }, LocalDateTime.of(1048, 1, 1, 0, 0));

		Person person = new Person("John", 'M', new Person[]{ child1, child2 }, LocalDateTime.of(1020, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "grandfather of Teresa";
		assertEquals(expected, result);
  }

  @Test
  public void PersonGreatGrandchildrenTest() {
		Person greatGrandchild1 = new Person("Andrew", 'M', null, LocalDateTime.of(1075, 1, 1, 0, 0));
		Person greatGrandchild2 = new Person("Jane", 'F', null, LocalDateTime.of(1073, 1, 1, 0, 0));

		Person grandchild1 = new Person("Teresa", 'F', 
				new Person[]{ greatGrandchild1 }, LocalDateTime.of(1065, 1, 1, 0, 0));
		Person grandchild2 = new Person("William", 'M', 
				new Person[]{ greatGrandchild2 }, LocalDateTime.of(1068, 1, 1, 0, 0));

		Person child1 = new Person("Holy", 'F', new Person[]{ grandchild1 }, LocalDateTime.of(1046, 1, 1, 0, 0));
		Person child2 = new Person("Mary", 'F', new Person[]{ grandchild2 }, LocalDateTime.of(1048, 1, 1, 0, 0));

		Person person = new Person("John", 'M', new Person[]{ child1, child2 }, LocalDateTime.of(1020, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "great-grandfather of Jane";
		assertEquals(expected, result);
  }

  @Test
  public void PersonGreatGrandchildrenMixedTest() {
		Person greatGrandchild1 = new Person("Andrew", 'M', null, LocalDateTime.of(1075, 1, 1, 0, 0));
		Person greatGrandchild2 = new Person("Jane", 'F', null, LocalDateTime.of(1073, 1, 1, 0, 0));
		Person greatGrandchild3 = new Person("Thomas", 'M', null, LocalDateTime.of(1074, 1, 1, 0, 0));

		Person grandchild1 = new Person("Teresa", 'F', 
				new Person[]{ greatGrandchild1, greatGrandchild2 }, LocalDateTime.of(1065, 1, 1, 0, 0));
		Person grandchild2 = new Person("William", 'M', 
				new Person[]{ greatGrandchild3 }, LocalDateTime.of(1068, 1, 1, 0, 0));
		Person grandchild3 = new Person("Richard", 'M', null, LocalDateTime.of(1068, 1, 1, 0, 0));

		Person child1 = new Person("Holy", 'F', new Person[]{ grandchild1 }, LocalDateTime.of(1046, 1, 1, 0, 0));
		Person child2 = new Person("Mary", 'F', new Person[]{ grandchild2, grandchild3 }, LocalDateTime.of(1048, 1, 1, 0, 0));

		Person person = new Person("John", 'M', new Person[]{ child1, child2 }, LocalDateTime.of(1020, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "great-grandfather of Jane";
		assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleGenerationsTest() {
		Person greatGreatGreatGrandchild1 = new Person("Josh", 'M', null, LocalDateTime.of(1098, 1, 2, 0, 0));
		Person greatGreatGreatGrandchild2 = new Person("Madeline", 'F', null, LocalDateTime.of(1097, 2, 2, 0, 0));
		Person greatGreatGreatGrandchild3 = new Person("Hunter", 'M', null, LocalDateTime.of(1097, 3, 1, 0, 0));
		Person greatGreatGreatGrandchild4 = new Person("Marisa", 'F', null, LocalDateTime.of(1097, 2, 1, 0, 0));

		Person greatGreatGrandchild1 = new Person("Thomas", 'M', 
				new Person[]{ greatGreatGreatGrandchild1, greatGreatGreatGrandchild2 }, LocalDateTime.of(1083, 1, 2, 0, 0));
		Person greatGreatGrandchild2 = new Person("Ana", 'F', 
				new Person[]{ greatGreatGreatGrandchild3 }, LocalDateTime.of(1086, 1, 1, 0, 0));
		Person greatGreatGrandchild3 = new Person("Phillip", 'M', 
				null, LocalDateTime.of(1083, 1, 1, 0, 0));
		Person greatGreatGrandchild4 = new Person("Richard", 'M', 
				new Person[]{ greatGreatGreatGrandchild4 }, LocalDateTime.of(1087, 1, 1, 0, 0));

		Person greatGrandchild1 = new Person("Andrew", 'M', 
				new Person[]{ greatGreatGrandchild1, greatGreatGrandchild2, greatGreatGrandchild4 }, LocalDateTime.of(1075, 1, 1, 0, 0));
		Person greatGrandchild2 = new Person("Jane", 'F', 
				new Person[]{ greatGreatGrandchild3 }, LocalDateTime.of(1073, 1, 1, 0, 0));

		Person grandchild1 = new Person("Teresa", 'F', 
				new Person[]{ greatGrandchild1 }, LocalDateTime.of(1065, 1, 1, 0, 0));
		Person grandchild2 = new Person("William", 'M', 
				new Person[]{ greatGrandchild2 }, LocalDateTime.of(1068, 1, 1, 0, 0));

		Person child1 = new Person("Holy", 'F', new Person[]{ grandchild1 }, LocalDateTime.of(1046, 1, 1, 0, 0));
		Person child2 = new Person("Mary", 'F', new Person[]{ grandchild2 }, LocalDateTime.of(1048, 1, 1, 0, 0));
		Person child3 = new Person("Josephine", 'F', null, LocalDateTime.of(1047, 1, 1, 0, 0));

		Person person = new Person("Caroline", 'F', new Person[]{ child1, child2, child3 }, LocalDateTime.of(1020, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "great-great-great-grandmother of Marisa";
		assertEquals(expected, result);
  }
}