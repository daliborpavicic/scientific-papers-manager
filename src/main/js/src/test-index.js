import test from 'ava';

test('my passing test', (t) => {
  const expected = true;
  const actual = true;

  t.is(actual, expected, 'actual value should be the same as expected');
});
