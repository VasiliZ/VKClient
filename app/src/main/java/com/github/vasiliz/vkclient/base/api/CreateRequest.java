package com.github.vasiliz.vkclient.base.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class CreateRequest {

    //TODO подумай о хорошем, о кодогенирации
    private String baseUrl;
    private StringBuilder mStringBuilder = new StringBuilder();
    private String TAG = CreateRequest.class.getSimpleName();

    private CreateRequest() {
    }

    private void buildApiTemplate(final Class service) {

        mStringBuilder.append(baseUrl);
        final Method[] methods = service.getMethods();
        for (final Method method : methods) {
            final GET annotation = method.getAnnotation(GET.class);
            if (annotation != null) {
                mStringBuilder.append(annotation.method())
                        .append("?");
            } else {
                throw new RuntimeException("The following annotations are necessary for correct work: @GET");
            }

            //Todo to another method maybe
            final Annotation[][] parameterAnnotation = method.getParameterAnnotations();
            for (final Annotation[] annotations : parameterAnnotation) {
                for (final Annotation annotation1 : annotations) {
                    if (annotation1 instanceof Query) {
                        final Query query = (Query) annotation1;
                        mStringBuilder.append(query.value())
                                .append("=")
                                .append("%s")
                                .append("&");
                    }
                }
            }
        }
        mStringBuilder.delete(mStringBuilder.length() - 1, mStringBuilder.length());

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
