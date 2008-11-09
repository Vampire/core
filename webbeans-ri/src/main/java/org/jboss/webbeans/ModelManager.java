package org.jboss.webbeans;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.jboss.webbeans.model.AnnotationModel;
import org.jboss.webbeans.model.BindingTypeModel;
import org.jboss.webbeans.model.ScopeModel;
import org.jboss.webbeans.model.StereotypeModel;

import com.google.common.collect.ForwardingMap;

public class ModelManager
{
   
   @SuppressWarnings("unchecked")
   private abstract class AnnotationModelMap<T extends AnnotationModel<?>> extends ForwardingMap<Class<? extends Annotation>, T>
   {

      Map<Class<? extends Annotation>, T> delegate;
      
      public AnnotationModelMap()
      {
         delegate = new HashMap<Class<? extends Annotation>, T>();
      }
      
      public <S extends Annotation> T putIfAbsent(Class<S> key)
      {
         if (!containsKey(key))
         {
            T model = createAnnotationModel(key);
            super.put(key, model);
            return model;
         }
         return (T) super.get(key);
      }
      
      protected  abstract <S extends Annotation> T createAnnotationModel(Class<S> type);
      
      @Override
      protected Map<Class<? extends Annotation>, T> delegate()
      {
         return delegate;
      }
      
   }
   
   @SuppressWarnings("unchecked")
   private class ScopeModelMap extends AnnotationModelMap<ScopeModel<?>>
   {
      
      @Override
      public <S extends Annotation> ScopeModel<S> putIfAbsent(Class<S> key)
      {
         return (ScopeModel<S>) super.putIfAbsent(key);
      }
      
      @Override
      protected <S extends Annotation> ScopeModel<?> createAnnotationModel(Class<S> type)
      {
         return new ScopeModel<S>(type);
      }
      
   }
   
   @SuppressWarnings("unchecked")
   private class BindingTypeModelMap extends AnnotationModelMap<BindingTypeModel<?>>
   {
      
      @Override
      public <S extends Annotation> BindingTypeModel<S> putIfAbsent(Class<S> key)
      {
         return (BindingTypeModel<S>) super.putIfAbsent(key);
      }
      
      @Override
      protected <S extends Annotation> BindingTypeModel<?> createAnnotationModel(Class<S> type)
      {
         return new BindingTypeModel<S>(type);
      }
      
   }
   
   private Map<Class<? extends Annotation>, StereotypeModel<?>> stereotypes = new HashMap<Class<? extends Annotation>, StereotypeModel<?>>();
   
   private ScopeModelMap scopes = new ScopeModelMap();
   
   private BindingTypeModelMap bindingTypes = new BindingTypeModelMap();
   

   public void addStereotype(StereotypeModel<?> stereotype)
   {
      stereotypes.put(stereotype.getType(), stereotype);
   }
   
   public StereotypeModel<?> getStereotype(Class<? extends Annotation> annotationType)
   {
      return stereotypes.get(annotationType);
   }
   
   public <T extends Annotation> ScopeModel<T> getScopeModel(Class<T> scopeType)
   {
      return scopes.putIfAbsent(scopeType);
   }
   
   public <T extends Annotation> BindingTypeModel<T> getBindingTypeModel(Class<T> bindingType)
   {
      return bindingTypes.putIfAbsent(bindingType);
   }
   
  

}
