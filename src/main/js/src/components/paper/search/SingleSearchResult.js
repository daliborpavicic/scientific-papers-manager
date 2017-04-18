import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Button, { buttonTypes } from '../../common/Button';

const PaperSearchResult = ({ hit, searchPapersStore }) => {
  const { id, title, highlightedText, numberOfImages, fileName } = hit;

  return (
    <div className='box'>
      <div className='content'>
        <strong>{title}</strong> <small>{`${numberOfImages} images`}</small>
        <p dangerouslySetInnerHTML={{ __html: highlightedText }} />
        <div className='control is-grouped'>
          <Button
            text={'Download'} type={buttonTypes.link}
            onClick={() => { searchPapersStore.downloadPaper(fileName); }}
          />
          <Button
            text={'More like this'}
            type={buttonTypes.link}
            onClick={() => { searchPapersStore.searchMoreLikeThis(id); }}
          />
        </div>
      </div>
    </div>
  );
};

PaperSearchResult.propTypes = {
  hit: PropTypes.shape({
    id: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    highlightedText: PropTypes.string.isRequired,
    numberOfImages: PropTypes.number.isRequired,
    fileName: PropTypes.string.isRequired
  }),
  searchPapersStore: PropTypes.object
};

export default inject('searchPapersStore')(observer(PaperSearchResult));
