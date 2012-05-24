package org.landal.hibernate.xstream;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.hibernate.collection.PersistentBag;
import org.hibernate.collection.PersistentList;
import org.hibernate.collection.PersistentMap;
import org.hibernate.collection.PersistentSet;
import org.hibernate.collection.PersistentSortedMap;
import org.hibernate.collection.PersistentSortedSet;
import org.hibernate.proxy.HibernateProxy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XstreamHibernateSerializer {

	protected XStream xstream;

	public XstreamHibernateSerializer() {
		xstream = new XStream() {
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new HibernateMapper(next);
			}
		};

		xstream.registerConverter(
				new HibernateProxyConverter(xstream.getMapper(),
						new PureJavaReflectionProvider()),
				XStream.PRIORITY_VERY_HIGH);

		xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentBag.class
						|| type == PersistentList.class
						|| type == PersistentSet.class
						|| type == PersistentSortedMap.class;
			}
		});

		xstream.registerConverter(new MapConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentMap.class
						|| type == PersistentSortedMap.class;
			}
		});

		xstream.autodetectAnnotations(true);
	}

	public String toXML(Object o) {
		return xstream.toXML(o);
	}

	public Object fromXML(String xml) {
		return xstream.fromXML(xml);
	}

	public Object fromXML(Reader xml) {
		return xstream.fromXML(xml);
	}

	class HibernateMapper extends MapperWrapper {

		Map collectionMap = new HashMap();

		public void init() {
			collectionMap.put(PersistentBag.class, ArrayList.class);
			collectionMap.put(PersistentList.class, ArrayList.class);
			collectionMap.put(PersistentMap.class, HashMap.class);
			collectionMap.put(PersistentSet.class, Set.class);
			collectionMap.put(PersistentSortedMap.class, SortedMap.class);
			collectionMap.put(PersistentSortedSet.class, SortedSet.class);
		}

		public HibernateMapper(Mapper arg0) {
			super(arg0);
			init();
		}

		public Class defaultImplementationOf(Class clazz) {

			if (collectionMap.containsKey(clazz)) {

				return (Class) collectionMap.get(clazz);
			}

			return super.defaultImplementationOf(clazz);
		}

		public String serializedClass(Class clazz) {
			// chekc whether we are hibernate proxy and substitute real name
			for (int i = 0; i < clazz.getInterfaces().length; i++) {
				if (HibernateProxy.class.equals(clazz.getInterfaces()[i])) {
					return clazz.getSuperclass().getName();
				}
			}
			if (collectionMap.containsKey(clazz)) {
				return ((Class) collectionMap.get(clazz)).getName();
			}

			return super.serializedClass(clazz);
		}

	}

	class HibernateProxyConverter extends ReflectionConverter {

		public HibernateProxyConverter(Mapper arg0, ReflectionProvider arg1) {
			super(arg0, arg1);

		}

		/**
		 * be responsible for hibernate proxy
		 */
		@Override
		public boolean canConvert(Class clazz) {

			return HibernateProxy.class.isAssignableFrom(clazz);
		}

		public void marshal(Object arg0, HierarchicalStreamWriter arg1,
				MarshallingContext arg2) {

			Object o = ((HibernateProxy) arg0).getHibernateLazyInitializer()
					.getImplementation();
			super.marshal(((HibernateProxy) arg0).getHibernateLazyInitializer()
					.getImplementation(), arg1, arg2);
		}

	}

}
