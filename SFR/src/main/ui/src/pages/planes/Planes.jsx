import React, {Component} from 'react';
import {Card, Nav, ListGroup, Row, Col, Table, Container} from "react-bootstrap";

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
                
                    <Container>
                <Table  hover >
                            <thead>
                                <tr>
                                <th>Nombre</th>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                <td>1</td>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                </tr>
                                <tr>
                                <td>2</td>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                                </tr>
                                
                            </tbody>
                            </Table>

                            </Container>
                        
                
                </Col>
                </Row>
                );
    }
};



export default Planes;
/*
<ListGroup variant="flush">
                        <ListGroup.Item>Cras justo odio</ListGroup.Item>
                        <ListGroup.Item>Dapibus ac facilisis in</ListGroup.Item>
                        <ListGroup.Item>Vestibulum at eros</ListGroup.Item>
                    </ListGroup> 
*/