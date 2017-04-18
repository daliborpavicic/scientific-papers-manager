import { observable, action } from 'mobx';
import { getCategories } from '../../api/paperApi';

const selectOptionsStore = () => {
  const state = observable({
    paperCategoryOptions: []
  });

  return {
    fetchPaperCategories: action(() => {
      getCategories().then(action((categories) => {
        state.paperCategoryOptions = categories.map((category) => {
          return {
            value: category.name,
            text: category.name
          };
        });
      }));
    }),
    getPaperCategoryOptions: () => state.paperCategoryOptions
  };
};

export default selectOptionsStore;
