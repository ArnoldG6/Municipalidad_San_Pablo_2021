//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import { Button, Nav, Row, Col, Table, Modal, Form } from "react-bootstrap";
import './plans.css';
class Planes extends Component {
   constructor(props){
       super(props);
       this.state={
           show: false,
           plan:{nombre:"", id:"", estado:"", autor:""},
           planes:[]
       };
       this.handleAddPlan = this.handleAddPlan.bind(this)
       this.handleOpenAddPlan = this.handleOpenAddPlan.bind(this)
   }
   handleOpenAddPlan=()=> {
       this.setState({show: !this.state.show});
   };
   handleAddPlan(){
       //let newPlan = this.state.plan
       //this.setState({plan: newPlan});
        let newList = this.state.planes
        newList.push(this.state.plan);
        this.setState({planes: newList});
   };
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
                    <Button id="NewItemButton" size="sm" onClick={this.handleOpenAddPlan}>Crear Item</Button>
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
                        {this.state.planes.map((plan) => {
                                        return (
                                        <tr key={plan.id}>
                                           <td>{plan.nombre}</td>
                                           <td>{plan.id}</td>
                                           <td>{plan.estado}</td>
                                           <td>{plan.autor}</td>
                                        </tr>
                                        )
                                    })}
                        </tbody>
                    </Table>
                </Col>
                <div>
                    <Modal show={this.state.show} >
                        <Modal.Header>
                            Nuevo Item
                        </Modal.Header>
                        <Modal.Body>
                            <div>
                            <cell>
                                <label>Nombre: &nbsp;&nbsp;</label>
                                <input type="text" placeholder="Nombre" value={this.state.plan.nombre} onChange={(e)=> this.setState({plan:{nombre:e.target.value}})}/>
                            </cell>
                            </div>
                            <div>
                            <cell>
                                <label>ID: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="text" placeholder="ID" value={this.state.plan.id} onChange={(e)=> this.setState({plan:{id:e.target.value}})}/>
                            </cell>
                            </div>
                            <div>
                            <cell>
                                <label>Estado: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="text" placeholder="Estado" value={this.state.plan.estado} onChange={(e)=> this.setState({plan:{estado:e.target.value}})}/>
                            </cell>
                            </div>
                            <div>
                            <cell>
                                <label>Autor: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="text" placeholder="Autor" value={this.state.plan.autor} onChange={(e)=> this.setState({plan:{autor:e.target.value}})}/>
                            </cell>
                            </div>
                        </Modal.Body>
                        <Modal.Footer>
                        <Button variant="primary" onClick={this.handleAddPlan}>                         
                            Save Changes
                        </Button>
                        <Button variant="secondary" onClick={this.handleOpenAddPlan}>                       
                            Close
                        </Button>
                        </Modal.Footer>
                    </Modal>
                </div>

            </Row>
        );
    }
};
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
                            </Form>

*/

