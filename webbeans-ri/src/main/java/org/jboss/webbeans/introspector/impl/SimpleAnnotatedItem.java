package org.jboss.webbeans.introspector.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.webbeans.TypeLiteral;

public class SimpleAnnotatedItem<T, S> extends AbstractAnnotatedItem<T, S>
{

   private Type[] actualTypeArguments = new Type[0];
   private Class<T> type;
   private Annotation[] actualAnnotations;
   
   private SimpleAnnotatedItem(Map<Class<? extends Annotation>, Annotation> annotationMap)
   {
      super(annotationMap);
   }
   
   private SimpleAnnotatedItem(Map<Class<? extends Annotation>, Annotation> annotationMap, Class<T> type)
   {
      super(annotationMap);
      this.type = type;
   }
   
   private SimpleAnnotatedItem(Map<Class<? extends Annotation>, Annotation> annotationMap, TypeLiteral<T> apiType)
   {
      super(annotationMap);
      this.type = apiType.getRawType();
      if (apiType.getType() instanceof ParameterizedType)
      {
         actualTypeArguments = ((ParameterizedType) apiType.getType()).getActualTypeArguments();
      }
   }
   
   private SimpleAnnotatedItem(Map<Class<? extends Annotation>, Annotation> annotationMap, Class<T> type, Type[] actualTypeArguments)
   {
      this(annotationMap, type);
      this.actualTypeArguments = actualTypeArguments;
   }
   
   public SimpleAnnotatedItem(Annotation[] annotations)
   {
      this(buildAnnotationMap(annotations));
      this.actualAnnotations = annotations;
   }
   
   public SimpleAnnotatedItem(Annotation[] annotations, Class<T> type)
   {
      this(buildAnnotationMap(annotations), type);
      this.actualAnnotations = annotations;
   }
   
   public SimpleAnnotatedItem(Annotation[] annotations, TypeLiteral<T> apiType)
   {
      this(buildAnnotationMap(annotations), apiType);
      this.actualAnnotations = annotations;
   }
   
   public SimpleAnnotatedItem(Annotation[] annotations, Class<T> type, Type[] actualTypeArguments)
   {
      this(buildAnnotationMap(annotations), type, actualTypeArguments);
      this.actualAnnotations = annotations;
   }

   public S getDelegate()
   {
      return null;
   }
   
   public Class<T> getType()
   {
      return type;
   }

   public Type[] getActualTypeArguments()
   {
      return actualTypeArguments;
   }
   
   public Annotation[] getActualAnnotations()
   {
      return actualAnnotations;
   }
   
   public boolean isStatic()
   {
      return false;
   }
   
   public boolean isFinal()
   {
      return false;
   }

   public String getName()
   {
      throw new IllegalArgumentException("Unable to determine name");
   }
   
}
