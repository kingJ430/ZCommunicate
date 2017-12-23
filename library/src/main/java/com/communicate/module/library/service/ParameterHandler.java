package com.communicate.module.library.service;


import com.communicate.module.library.router.RouterRequest;

import java.io.IOException;

abstract class ParameterHandler<T> {
  abstract void apply(RouterRequest routerRequest, T value) throws IOException;


    static final class Quary<T> extends ParameterHandler<T> {
        private final String name;

        Quary(String name) {
            this.name = name;
        }

        @Override void apply(RouterRequest routerRequest, T value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException(
                        "RouterPath parameter \"" + name + "\" value must not be null.");
            }
            routerRequest.data(name,String.valueOf(value));
        }

    }


  static final class Context<T> extends ParameterHandler<T> {
    private final String name;

    Context(String name) {
      this.name = name;
    }

    @Override void apply(RouterRequest routerRequest, T value) throws IOException {
      if (value == null) {
        throw new IllegalArgumentException(
            "RouterPath parameter \"" + name + "\" value must not be null.");
      }
      routerRequest.context((android.content.Context) value);
    }

  }

    static final class Data<T> extends ParameterHandler<T> {
        private final String name;

        Data(String name) {
            this.name = name;
        }

        @Override void apply(RouterRequest routerRequest, T value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException(
                        "RouterPath parameter \"" + name + "\" value must not be null.");
            }
            routerRequest.reqeustObject(value);
        }

    }

}
