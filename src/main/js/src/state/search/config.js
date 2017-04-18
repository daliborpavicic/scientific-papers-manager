//   TITLE("title"),
//   ABSTRACT("anAbstract"),
//   KEYWORDS("keywords"),
//   TEXT("text"),
//   AUTHOR("authorName");
//   CATEGORY("categoryName"),
//   PUBLISH_DATE("publishDate"),

// queryString;
// queryType;
// boolOccurrence;

export const boolOccurOptions = [{
  value: 'MUST', text: 'and',
}, {
  value: 'SHOULD', text: 'or',
}, {
  value: 'MUST_NOT', text: 'and not',
}];


// DEFAULT, FUZZY, PHRASE, RANGE, PREFIX, WILDCARD
export const queryTypeOptions = [{
  value: 'DEFAULT', text: 'term',
}, {
  value: 'FUZZY', text: 'fuzzy',
}, {
  value: 'PHRASE', text: 'phrase',
}, {
  value: 'PREFIX', text: 'prefix',
}, {
  value: 'WILDCARD', text: 'wildcard',
}];

const simpleSearch = {
  query: { label: '' }
};


export const paperFields = {
  title: { name: 'title', label: 'Title' },
  anAbstract: { name: 'anAbstract', label: 'Abstract' },
  keywords: { name: 'keywords', label: 'Keywords' },
  text: { name: 'text', label: 'Text' },
  authorName: { name: 'authorName', label: 'Author' },
  categoryName: { name: 'categoryName', label: 'Category' },
  publishDate: { name: 'publishDate', label: 'Publish date' },
};

const queryStringSuffix = '_queryString';
const queryTypeSuffix = '_queryType';
const boolOccurSuffix = '_boolOccurrence';

export const getQueryStringFieldName = paperFieldName => `${paperFieldName}${queryStringSuffix}`;
export const getQueryTypeFieldName = paperFieldName => `${paperFieldName}${queryTypeSuffix}`;
export const getBoolOccurrenceFieldName = paperFieldName => `${paperFieldName}${boolOccurSuffix}`;

const advancedSearch = {};

Object.keys(paperFields).forEach((fieldKey) => {
  const paperFieldName = paperFields[fieldKey].name;

  advancedSearch[getQueryStringFieldName(paperFieldName)] = {};
  advancedSearch[getQueryTypeFieldName(paperFieldName)] = { initialValue: 'DEFAULT' };
  advancedSearch[getBoolOccurrenceFieldName(paperFieldName)] = { initialValue: 'MUST' };
});

Object.assign(advancedSearch, {
  category: { label: paperFields.categoryName.label },
  dateFrom: { label: 'Publish date from' },
  dateTo: { label: 'Publish date to' }
});

export { simpleSearch, advancedSearch };

