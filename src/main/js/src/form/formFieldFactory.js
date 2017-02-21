import { action, observable } from 'mobx';

function formFieldFactory({
  name,
  label = '',
  initialValue = '',
  validators = [],
}) {
  const state = observable({
    name: observable.ref(name),
    initialValue,
    label,
    value: initialValue,
    isTouched: false,
    validators: observable.ref(validators),
    get errors() {
      return this.validators
      .map(validate => validate(this.value, this))
      .filter(result => typeof result === 'string');
    },
    get isValid() {
      return this.errors.length === 0;
    },
    get isDirty() {
      return this.initialValue !== this.value;
    },
    get hasErrors() {
      return this.errors.length > 0;
    }
  });

  const publicAPI = {
    // region Getters
    isTouched: () => state.isTouched,
    getName: () => state.name,
    getValue: () => state.value,
    getLabel: () => state.label,
    isValid: () => state.isValid,
    isDirty: () => state.isDirty,
    hasErrors: () => state.hasErrors,
    getErrors: () => state.errors,
    // endregion

    // region Actions
    addValidators: action('addValidators', (...validators) => {
      validators.forEach(validator => state.validators.push(validator));
    }),

    setValue: action('setValue', (value) => {
      state.value = value;
      state.isTouched = true;
    }),

    setLabel: action('setLabel', (labelValue) => {
      state.label = labelValue;
    }),

    setInitialValue: action('setInitialValue', (value, reset = true) => {
      state.initialValue = value;

      if (reset) {
        state.value = value;
        state.isTouched = false;
      }
    }),

    reset: action('reset', () => {
      state.value = state.initialValue;
      state.isTouched = false;
    })
    // endregion
  };

  return publicAPI;
}

export default formFieldFactory;