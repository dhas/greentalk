import React, { Component } from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { ReactComponent as Tree } from './icons/tree.svg';
import { ReactComponent as Shrub } from './icons/bush.svg';

class GardenGrid extends Component {
  render() {
    const { highlightGrid, gardenObjects } = this.props;
    const numbers = [1, 2, 3]
    const letters = ['A', 'B', 'C']

    return (
      <Container className='border border-2'>
        {letters.map(letter => {
          return <Row>{
            numbers.map(number => {
              const gridValue = `${letter}${number}`
              const hasObject = gardenObjects.find(obj => obj.position === gridValue)

              return <Col
                key={gridValue}
                sm={4}
                className={`text-left fw-bold border border-2 ${highlightGrid === gridValue && !hasObject?.object ? 'bg-success' : ''
                  }`}
                style={{ height: '200px' }}
              >
                {gridValue}
                {hasObject?.object === 'tree' && <Tree className='w-100 h-100' />}
                {hasObject?.object === 'bush' && <Shrub className='w-100 h-100' />}
              </Col>
            })}
          </Row>
        })}
      </Container >
    );
  };
}

export default GardenGrid;
