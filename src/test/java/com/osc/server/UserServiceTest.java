package com.osc.server;

import com.osc.server.model.User;
import com.osc.server.repository.IUserRepository;
import com.osc.server.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    IUserRepository repository;

    @InjectMocks
    UserService service;

    @Mock
    Pageable pageable;

    @Mock
    Page<User> samplePage;

    @Mock
    private User sampleData;

    @Before
    public void init() {
        sampleData.setId(1);
        sampleData.setEmail("test@email.com");
        sampleData.setFirstName("First Name");
        sampleData.setLastName("Last Name");

    }

    @Test
    public void read() {
        when(repository.findAll(pageable)).thenReturn(samplePage);
        Page<User> serviceResult = service.read(pageable);

        Assert.assertEquals(serviceResult.getTotalPages(), samplePage.getTotalPages());
    }

    @Test
    public void create() {

        when(repository.save(sampleData)).thenReturn(sampleData);
        User serviceResult = service.create(sampleData);

        Assert.assertEquals(serviceResult.getEmail(), sampleData.getEmail());
        Assert.assertEquals(serviceResult.getFirstName(), sampleData.getFirstName());
        Assert.assertEquals(serviceResult.getLastName(), sampleData.getLastName());
    }

    @Test
    public void update() {
        when(repository.save(sampleData)).thenReturn(sampleData);
        User serviceResult = service.update(sampleData.getId(), sampleData);

        Assert.assertEquals(serviceResult.getEmail(), sampleData.getEmail());
        Assert.assertEquals(serviceResult.getFirstName(), sampleData.getFirstName());
        Assert.assertEquals(serviceResult.getLastName(), sampleData.getLastName());
    }

    @Test
    public void delete() {
        service.delete(sampleData.getId());
    }

    @Test
    public void get() {
        when(repository.getOne(sampleData.getId())).thenReturn(sampleData);
        User serviceResult = service.get(sampleData.getId());

        Assert.assertNotNull(serviceResult);
    }
}
