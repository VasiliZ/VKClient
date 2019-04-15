package com.github.vasiliz.vkclient.base.api;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class CreateRequest {

    private String baseUrl;
    private StringBuilder mStringBuilder = new StringBuilder();
    private String TAG = CreateRequest.class.getSimpleName();

    private CreateRequest() {
    }

    private void buildApiTemplate(final Class service) {

        try {
            mStringBuilder.append(baseUrl);
            final Method[] methods = service.getMethods();
            for (final Method method : methods) {
                final Annotation annotation = method.getAnnotation(GET.class);
                if (annotation instanceof GET) {
                    final GET get = (GET) annotation;
                    get.method();
                    mStringBuilder.append(get.method())
                            .append("?");
                }
                final Annotation[][] parameterAnnotation = method.getParameterAnnotations();
                for (final Annotation[] annotations : parameterAnnotation) {
                    for (final Annotation annotation1 : annotations) {
                        final Query query = (Query) annotation1;
                        mStringBuilder.append(query.value())
                                .append("=")
                                .append("%s")
                                .append("&");
                    }
                }
            }
            mStringBuilder.delete(mStringBuilder.length()-1, mStringBuilder.length());
        } catch (final Exception e) {
            Log.d(TAG, "buildApiTemplate: " + "don't find annotations for work with api");
        }
    }

    public String getTamplateApiString() {
        return mStringBuilder.toString();
    }

    public static final class Builder {

        private String mBaseUrl;

        public Builder setServiceClass(final Class<?> pServiceClass) {
            mServiceClass = pServiceClass;
            return this;
        }

        private Class<?> mServiceClass;

        public Builder setBaseUrl(final String pBaseUrl) {
            mBaseUrl = pBaseUrl;
            return this;
        }

        public CreateRequest build() {
            final CreateRequest createRequest = new CreateRequest();
            createRequest.baseUrl = this.mBaseUrl;
            createRequest.buildApiTemplate(this.mServiceClass);
            return createRequest;
        }
    }

}
