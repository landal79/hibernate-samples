package genericdao.finder;

import org.hibernate.type.Type;

/**
 * Used to locate any specific type mappings that might be necessary for a dao implementation
 */
public interface FinderArgumentTypeFactory
{
    Type getArgumentType(Object arg);
}
