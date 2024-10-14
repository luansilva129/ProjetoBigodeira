package br.com.atlas.bigodeira.backend.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import br.com.atlas.bigodeira.backend.domainBase.EntidadeBase;
import br.com.atlas.bigodeira.backend.service.CrudRepositoryCustom;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.GenericTypeResolver;
import static org.apache.commons.configuration2.beanutils.BeanHelper.setProperty;
import static org.atmosphere.util.IntrospectionUtils.getProperty;


public class CrudControllerBase<T extends EntidadeBase, S extends Map<String, Object>, R extends CrudRepositoryCustom<T>>
        extends ControllerBase {

    protected static final String RESULT = "result";

    @Getter
    @Setter
    private boolean showNewButton = true;
    @Getter
    @Setter
    private boolean showCancelButton = true;
    @Getter
    @Setter
    private boolean showSaveButton = true;
    @Setter
    private boolean showDeleteButton = true;

    public boolean isShowDeleteButton() {
        return editForm != null && editForm.getId() != null && showDeleteButton;
    }

    protected R repository;

    @Getter
    protected S searchForm;

    @Getter
    @Setter
    protected T selected;


    protected final Class<S> searchFormType;

    @Getter
    protected T editForm;

    protected final Class<T> editFormType;


    protected S newSearchFormInstance() {
        try {
            return searchFormType.newInstance();
        } catch (Exception e) {
            // Should Not Happen
            throw new RuntimeException(e);
        }
    }
    protected T newEditFormInstance() {
        try {
            return editFormType.newInstance();
        } catch (Exception e) {
            // Should Not Happen
            throw new RuntimeException(e);
        }
    }

    protected static final String CRUD_SEARCH = "crudSearch";

    protected final Object crudSearchInstance;
    protected final Method crudSearchMethod;
    protected final String[] searchFormAttributes;

    protected Method findCrudSearchMethod(Class<?> clazz) {
        try {
            for (final Method method : clazz.getMethods()) {
                if (method.getName().equals(CRUD_SEARCH)) {
                    return method;
                }
            }
            return null;
        } catch (SecurityException e) {
            throw new RuntimeException("_findCrudSearchMethod: Falha localizando " + CRUD_SEARCH, e);
        }
    }

    @SuppressWarnings("unchecked")
    public CrudControllerBase() {
        final java.lang.Class<?>[] typeArguments =
                GenericTypeResolver.resolveTypeArguments(this.getClass(), CrudControllerBase.class);
        editFormType = (Class<T>) typeArguments[0];
        searchFormType = (Class<S>) typeArguments[1];
        Method theMethod = findCrudSearchMethod(this.getClass());
        if (theMethod == null) {
            theMethod = findCrudSearchMethod(repository.getClass());
            if (theMethod == null) {
                throw new NoSuchMethodError(CRUD_SEARCH);
            } else {
                crudSearchInstance = repository;
                crudSearchMethod = theMethod;
            }
        } else {
            crudSearchInstance = this;
            crudSearchMethod = theMethod;
        }

        final int paramCount = crudSearchMethod.getParameterCount();
        searchFormAttributes = new String[paramCount];
        int idx = 0;
        for (final Parameter param : crudSearchMethod.getParameters()) {
            searchFormAttributes[idx++] = param.getName();
        }
    }

    @Override
    public void postConstruct() throws Exception {
        super.postConstruct();
        searchForm = newSearchFormInstance();
    }


    public boolean isEditing() {
        return editForm != null;
    }

    public void cancel() {
        editForm = null;
    }

    protected Object getSearchFormResult() {
        return getProperty(searchForm, RESULT);
    }

    protected void setSearchFormResult(Object obj) {
        setProperty(searchForm, RESULT, obj);
    }



    protected void doSave(final T entity) throws Exception {
        repository.save(entity);
    }

    protected Object callServiceMethod(final Object instance, final Method method, final Object[] args) {
        try {
            return AopUtils.invokeJoinpointUsingReflection(instance, method, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void search() {

        final Object[] args = new Object[searchFormAttributes.length];
        int i = 0;
        for (final String attribute : searchFormAttributes) {
            Object arg = getProperty(searchForm, attribute);
            args[i++] = StringUtils.isBlank(Objects.toString(arg)) ? null : arg;
        }

        setProperty(searchForm, RESULT, callServiceMethod(crudSearchInstance, crudSearchMethod, args));
    }

    }


