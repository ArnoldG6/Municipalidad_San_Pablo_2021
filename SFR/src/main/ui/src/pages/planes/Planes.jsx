import { render } from '@testing-library/react';
import React, { Component } from 'react';
import { Button, Nav, ListGroup, Row, Col, Table, Container, Modal, Form } from "react-bootstrap";
import './plans.css';



class Planes extends Component {



    render() {
        return (
            <Row className="Content">
                <Col md={1} sm={3} className="Sidebar">

                    <Nav defaultActiveKey="/home" className="flex-column">
                        <Nav.Link href="/home">Proyectos</Nav.Link>
                        <Nav.Link href="/home">Procesos</Nav.Link>

                    </Nav>

                </Col>
                <Col md={11} sm={9}>

                    <Button id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                    <form action="" class="algo">
                        <input type="text" id="fname" name="buscadfadfr" placeholder="Buscar"></input>
                    </form>




                    <Table hover>
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th>Autor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><a href="/home">Ricardo antonio</a></td>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td><div class="state">xD</div></td>
                                <td>xD</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                                <td>xD</td>
                            </tr>

                        </tbody>
                    </Table>
                </Col>

                <div>
                    <Modal show={false}>
                        <Modal.Header>
                            Nuevo Item
                        </Modal.Header>
                        <Modal.Body>

                            <Form>
                                <Form.Group className="mb-3" controlId="formNewPlanName">
                                    <Form.Label>Nombre</Form.Label>
                                    <Form.Control size="lg" type="text" />
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="formNewPlanID">
                                    <Form.Label>ID</Form.Label>
                                    <Form.Control size="lg" type="text" />
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="formNewPlanStatus">
                                    <Form.Label>Estado</Form.Label>
                                    <Form.Control size="lg" type="text" />
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="formNewPlanAuthor">
                                    <Form.Label>Autor</Form.Label>
                                    <Form.Control size="lg" type="text" />
                                </Form.Group>

                                <Form>
                                    {['radio'].map((type) => (
                                        <div key={`inline-${type}`} className="mb-3">
                                            <Form.Check
                                                inline
                                                label="Proyecto"
                                                name="group1"
                                                type={type}
                                                id={`inline-${type}-1`}
                                            />
                                            <Form.Check
                                                inline
                                                label="Proceso"
                                                name="group1"
                                                type={type}
                                                id={`inline-${type}-2`}
                                            />

                                        </div>
                                    ))}
                                </Form>
                                {
                                 //   <Form.Group className="mb-3" controlId="formNewPlanFiles">
                                 //       <Form.Label>Datos adjuntos</Form.Label>
                                 //       <Form.Control type="file" size="sm" />
                                 //  </Form.Group>
                                }

                                <Button id="formNewItemButton" size="sm" onClick={this.closeModal}>Crear Item</Button>
                            </Form>

                        </Modal.Body>
                    </Modal>
                </div>

            </Row>
        );
    }
};

function search() {
    return 1;


}





export default Planes;
/*
var Plan = {nombre: 'xd', fecha: 123, estado: 'xd', autor: "Esteban1"};
function addPlan() {
    let request = new Request('http://localhost:3000/SFR/' + 'Plans', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(Plan)});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {

            return;
        }
        document.location = 'http://localhost:8080/:/SFR/';
    })();
}
*/

