//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import './Planes.css';
import { Button, Stack, Row, Table } from "react-bootstrap";
import AddPlanModal from './Components/AddPlanModal';
import axios from 'axios';

class Planes extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            planes: []
        };
        this.openModal = this.openModal.bind(this)
        this.closeModal = this.closeModal.bind(this)
        this.updatePlanes = this.updatePlanes.bind(this)
    }
    componentDidMount(){
        //alert("dwd");
        let options={
            url: "http://localhost:8080/SFR/API/PlanServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }
        axios(options).then(response => {
            this.setState({planes:response.data})            
        });
    }
    updatePlanes(newPlan){
        let newList = this.state.planes;
        newList.push(newPlan);
       this.setState({planes: newList})
    };
    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    render() {
        return (
            

            <div className="Planes-Container container-fluid">
               

                <Row className="mt-2">
                    <Stack direction="horizontal" gap={3}>
                        <Button className="btn-sfr" id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                        <form action="" className="algo ms-auto">
                            <input type="text" id="fname" name="buscadfadfr" placeholder="Buscar"></input>
                        </form>
                    </Stack>
                </Row>
                <Row>
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
                                        <td>{plan.name}</td>
                                        <td>{plan.id}</td>                                                       
                                        <td>{plan.entryDate}</td>
                                        <td>{plan.description}</td>
                                        <td>{plan.authorName}</td>
                                    </tr>
                                )
                            })}
                        </tbody>
                    </Table>

                    <AddPlanModal updatePlanes={this.updatePlanes} show={this.state.show} closeModal={this.closeModal}/>

                </Row>
            </div>
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