package org.testcharm.dal.runtime;

import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;
import org.testcharm.util.BeanClass;

public abstract class TextFormatter<F, T> {

    public static final TextFormatter<String, String> BASE_FORMATTER = new TextFormatter<String, String>() {
        @Override
        protected String format(String content, TextAttribute attribute, DALRuntimeContext context) {
            String text = content.replace(attribute.endOfLine() + "\n", attribute.newLine())
                    .replace(attribute.continuation() + "\n", "")
                    .replace("\n", attribute.newLine());
            if (text.endsWith(attribute.endOfLine()))
                return text.substring(0, text.length() - attribute.endOfLine().length());
            return text;
        }
    };
    public static final TextFormatter<String, String> LF_NL = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use \\n as new line";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.newLine("\n");
        }
    };
    public static final TextFormatter<String, String> CR_NL = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use \\r as new line";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.newLine("\r");
        }
    };
    public static final TextFormatter<String, String> CRLF_NL = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use \\r\\n as new line";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.newLine("\r\n");
        }
    };
    public static final TextFormatter<String, String> LT_EOL = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use < as end of line character";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.endOfLine("<");
        }
    };
    public static final TextFormatter<String, String> BSL_CONT = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use \\ as line continuation character";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.continuation("\\");
        }
    };
    public static final TextFormatter<String, String> RET_EOL = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use ⏎ as end of line character";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.endOfLine("⏎");
        }
    };
    public static final TextFormatter<String, String> NL = new TextFormatter<String, String>() {
        @Override
        public String description() {
            return "use system new line as new line";
        }

        @Override
        protected TextAttribute attribute(TextAttribute attribute) {
            return attribute.newLine(System.lineSeparator());
        }
    };
    public static final TextFormatter<String, String> DEFAULT = BASE_FORMATTER.merge(LF_NL)
            .merge(LT_EOL).merge(BSL_CONT);

    @SuppressWarnings("unchecked")
    protected T format(F content, TextAttribute attribute, DALRuntimeContext context) {
        return (T) content;
    }

    protected TextAttribute attribute(TextAttribute attribute) {
        return attribute;
    }

    final public <T2, R> TextFormatter<F, R> merge(TextFormatter<T2, R> another) {
        return new TextFormatter<F, R>() {
            @Override
            protected TextAttribute attribute(TextAttribute attribute) {
                return another.attribute(TextFormatter.this.attribute(attribute));
            }

            @Override
            protected R format(F content, TextAttribute attribute, DALRuntimeContext context) {
                T2 formatted = context.getConverter().convert(another.acceptType(),
                        TextFormatter.this.format(content, attribute, context));
                return another.format(formatted, attribute, context);
            }

            @Override
            public Class<F> acceptType() {
                return TextFormatter.this.acceptType();
            }

            @Override
            public Class<R> returnType() {
                return another.returnType();
            }
        };
    }

    final public T format(F content, DALRuntimeContext context) {
        return format(content, attribute(new TextAttribute("", "", "")), context);
    }

    @SuppressWarnings("unchecked")
    public Class<F> acceptType() {
        return (Class<F>) BeanClass.create(getClass()).getSuper(getClass().getSuperclass()).getTypeArguments(0)
                .orElseThrow(() -> new IllegalStateException("Cannot guess type via generic type argument, please override TextFormatter::acceptType"))
                .getType();
    }

    @SuppressWarnings("unchecked")
    public Class<T> returnType() {
        return (Class<T>) BeanClass.create(getClass()).getSuper(getClass().getSuperclass()).getTypeArguments(1)
                .orElseThrow(() -> new IllegalStateException("Cannot guess type via generic type argument, please override TextFormatter::returnType"))
                .getType();
    }

    public String description() {
        return "a customized formatter";
    }

    public String fullDescription() {
        return description() + "\n        Accept: " + acceptType().getName() + "\n        Return: " + returnType().getName();
    }
}
