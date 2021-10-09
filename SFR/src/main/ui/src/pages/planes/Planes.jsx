import React, {Component} from 'react';
import {Card, Nav, ListGroup, Row, Col} from "react-bootstrap";
class Planes extends Component {
    render() {
        return (           
                <Row className="Content">
                <Col md={1} sm={3} className="Sidebar">
                <Nav defaultActiveKey="/home" className="flex-column">
                    <Nav.Link href="/home">Active</Nav.Link>
                    <Nav.Link eventKey="link-1">Link</Nav.Link>
                    <Nav.Link eventKey="link-2">Link</Nav.Link>
                    <Nav.Link eventKey="disabled" disabled>
                        Disabled
                    </Nav.Link>
                </Nav>
                </Col>
                <Col md={11} sm={9}>
                <Card>
                    <ListGroup variant="flush">
                        <ListGroup.Item>Cras justo odio</ListGroup.Item>
                        <ListGroup.Item>Dapibus ac facilisis in</ListGroup.Item>
                        <ListGroup.Item>Vestibulum at eros</ListGroup.Item>
                    </ListGroup>
                </Card>
                </Col>
                </Row>
                );
    }
};
export default Planes;
