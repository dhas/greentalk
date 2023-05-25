import React, { Component } from 'react'
import FurhatGUI from 'furhat-gui'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import GardenGrid from './GardenGrid'


class App extends Component {

    constructor(props) {
        super(props)
        this.state = {
            "speaking": false,
            "cols": [],
            "rows": [],
            "gardenObjects": [],
            "highlightGrid": null,
            "gardenState": []
        }
        this.furhat = null
    }

    setupSubscriptions() {
        // Our DataDelivery event is getting no custom name and hence gets it's full class name as event name.
        this.furhat?.subscribe('furhatos.app.furgui.DataDelivery', (data) => {
            this.setState({
                ...this.state,
                rows: data.rows,
                cols: data.cols
            })
        })

        this.furhat?.subscribe('furhatos.app.furgui.AddGardenState', (data) => {
            this.setState({
                ...this.state,
                gardenState: [...this.state.gardenObjects, {
                    position: data.gridPosition ?? null,
                    object: data.gardenObject.value ?? null
                }]

            })
        })

        this.furhat?.subscribe('furhatos.app.furgui.SelectGrid', (data) => {
            this.setState({
                ...this.state,
                highlightGrid: data.select.value ?? null
            })
        })

    }

    componentDidMount() {
        FurhatGUI()
            .then(connection => {
                this.furhat = connection
                this.setupSubscriptions()
            })
            .catch(console.error)
    }


    clickGrid = (position) => {
        console.log(position)
        this.furhat?.send({
            event_name: "ClickGrid",
            data: position
        })

        this.setState({
            ...this.state,
            highlightGrid: position ?? null
        })
    }

    render() {
        return (
            <Container>
                <Row className='d-flex'>
                    <Col sm={12}>
                        <h1>greentalk</h1>
                    </Col>
                </Row>
                <Row>
                    <GardenGrid
                        {...this.state}
                        clickGrid={this.clickGrid}
                    />
                </Row>
            </Container>
        )
    }

}

export default App;
