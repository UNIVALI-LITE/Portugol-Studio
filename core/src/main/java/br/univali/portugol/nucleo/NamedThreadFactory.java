package br.univali.portugol.nucleo;

import java.util.concurrent.ThreadFactory;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/01/2016
 */
public final class NamedThreadFactory implements ThreadFactory
{
    private Integer count = -1;
    
    private final String nameFormat;
    private final Integer priority;

    public NamedThreadFactory()
    {
        this.nameFormat = "Thread";
        this.priority = Thread.NORM_PRIORITY;
    }
    
    public NamedThreadFactory(String nameFormat)
    {
        this.nameFormat = nameFormat;
        this.priority = Thread.NORM_PRIORITY;
    }
    
    public NamedThreadFactory(Integer priority)
    {
        this.nameFormat = "Thread";
        this.priority = priority;
    }

    public NamedThreadFactory(String nameFormat, Integer priority)
    {
        this.nameFormat = nameFormat;
        this.priority = priority;
    }    

    @Override
    public Thread newThread(Runnable runnable)
    {
        count++;
        String name;
                
        Thread thread = new Thread(runnable);

        if (nameFormat.contains("%d"))
        {
            name = String.format(nameFormat, count);
        }
        else
        {
            name = nameFormat;
        }
        
        thread.setName(name);
        thread.setPriority(priority);
        
        return thread;
    }    
}
