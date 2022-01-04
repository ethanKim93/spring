package com.example.demo.Service;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.List; 
import java.util.Optional;



@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public String testService(){
		//
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		//
		repository.save(entity);
		//
		TodoEntity saveEntity = repository.findById(entity.getId()).get();
		return saveEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity) {
		
		validate(entity);
		
		repository.save(entity);
		log.info("Entity Id:{} is saved",entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	// �����丵�� �޼ҵ�
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserId() == null) {
			log.warn("Unkown user");
			throw new RuntimeException("Unkown user");
		}
		
	}
	
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity) {
		// (1) ���� �� ��ƼƼ�� ��ȿ���� Ȯ���Ѵ�. �� �޼���� 2.3.1 Create Todo���� �����ߴ�.
		validate(entity);

		// (2) �Ѱܹ��� ��ƼƼ id�� �̿��� TodoEntity�� �����´�. �������� �ʴ� ��ƼƼ�� ������Ʈ �� �� ���� �����̴�.
		final Optional<TodoEntity> original = repository.findById(entity.getId());


		original.ifPresent(todo -> {
			// (3) ��ȯ�� TodoEntity�� �����ϸ� ���� �� entity�� ������ ���� �����.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());

			// (4) �����ͺ��̽��� �� ���� �����Ѵ�.
			repository.save(todo);
		});

		// 2.3.2 Retrieve Todo���� ���� �޼��带 �̿��� ������ ��� Todo ����Ʈ�� �����Ѵ�.
		return retrieve(entity.getUserId());
	}


	public List<TodoEntity> delete(final TodoEntity entity) {
		// (1) ���� �� ��ƼƼ�� ��ȿ���� Ȯ���Ѵ�. �� �޼���� 2.3.1 Create Todo���� �����ߴ�.
		validate(entity);

		try {
			// (2) ��ƼƼ�� �����Ѵ�.
			repository.delete(entity);
		} catch(Exception e) {
			// (3) exception �߻��� id�� exception�� �α��Ѵ�.
			log.error("error deleting entity ", entity.getId(), e);

			// (4) ��Ʈ�ѷ��� exception�� ������. �����ͺ��̽� ���� ������ ĸ��ȭ �ϱ� ���� e�� �������� �ʰ� �� exception ������Ʈ�� �����Ѵ�.
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		// (5) �� Todo����Ʈ�� ������ �����Ѵ�.
		return retrieve(entity.getUserId());
	}
}
