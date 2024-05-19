package com.premiumminds.internship.teknonymy;

import java.util.LinkedList;
import java.util.Queue;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    if (person == null) return null;
    
    Queue<Person> queue = new LinkedList<>();
    queue.add(person);
    
    int depth = 0;
    Person oldest = null;
    while (!queue.isEmpty()) {
      boolean foundNewGen = false;
      
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Person current = queue.poll();
        if (current.children() == null) continue;

        for (Person child : current.children()) {
          if (child == null) continue;
          queue.add(child);

          if (!foundNewGen) {
            foundNewGen = true;
            oldest = child;
            depth++;
          } else {
            oldest = child.dateOfBirth().isBefore(oldest.dateOfBirth()) ? child : oldest;
          }
        }
      }
    }

    if (depth == 0) return "";

    StringBuilder teknonymy = new StringBuilder();

    if (depth == 1) {
      teknonymy.append(person.sex() == 'F' ? "mother of " : "father of ");
    }
    else {
      teknonymy.append("great-".repeat(depth - 2));
      teknonymy.append(person.sex() == 'F' ? "grandmother of " : "grandfather of ");
    }

    teknonymy.append(oldest.name());

    return teknonymy.toString();
  };
}
