import React, { Component } from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Icons from './Icons';

class GardenGrid extends Component {

  render() {
    const { highlightGrid,
      gardenState,
      rows,
      cols,
      clickGrid
    } = this.props;

    return (
      <Container className='border border-2'>
        {rows.map(letter => {
          return <Row>{
            cols.map(number => {
              const gridValue = `${letter}${number}`
              const hasObject = gardenState.find(obj => obj.position === gridValue)
              return <Col
                key={gridValue}
                sm={4}
                className={`text-left fw-bold border border-2 
                ${highlightGrid === gridValue && !hasObject?.object ? 'bg-success' : ''
                  }`}
                style={{ height: '200px' }}

                onClick={() => clickGrid(`${letter}${number}`)}
              >
                {gridValue}

                {hasObject?.object &&
                  <Icons iconKey={hasObject.object} />
                }
              </Col>
            })}
          </Row>
        })}
      </Container >
    );
  };
}

export default GardenGrid;
