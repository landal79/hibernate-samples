package genericdaotest.dao;

import java.util.List;
import java.util.Iterator;

import genericdao.GenericDao;
import genericdaotest.domain.Person;

public interface PersonDao extends GenericDao<Person, Long>
{
    List<Person> findByName(String name);
    Iterator<Person> iterateByWeight(int weight);
}
