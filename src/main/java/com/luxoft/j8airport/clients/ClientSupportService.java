package com.luxoft.j8airport.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientSupportService
{
    private static final String DEFAULT_CLIENT_NAME_PREFIX = "CLONE_";

    private static int counter = 0;

    @Autowired
    private ClientRepository repository;

    public void generateAndStoreClients()
    {
        repository.save(generateClient("Oleg", 34, Client.Gender.MALE));
    }

    @Transactional
    public void deleteAllGeneratedClients()
    {
        repository.deleteAllByNameStartsWith(DEFAULT_CLIENT_NAME_PREFIX);
    }

    public List<Client> generateAndStoreClients(Status status, int count)
    {
        List<Client> clients = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
        {
            Client client = generateClient(DEFAULT_CLIENT_NAME_PREFIX + counter++,
                    21, (i % 2 == 0 ? Client.Gender.MALE : Client.Gender.FEMALE));

            client.setStatus(status);

            clients.add(repository.save(client));
        }

        return clients;
    }

    public Client generateAndStoreClient(String name, int age, Client.Gender gender)
    {
        return repository.save(generateClient(name, age, gender));
    }

    private Client generateClient(String name, int age, Client.Gender gender)
    {
        Client c = new Client();

        c.setName(name);
        c.setAge(age);
        c.setGender(gender);

        return c;
    }
}
