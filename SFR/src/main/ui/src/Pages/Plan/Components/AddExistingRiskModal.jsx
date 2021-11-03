import React, { Component } from 'react';
import axios from 'axios';
import '../Plan.css';
import { Modal, Button, Form, Table, FormGroup } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class AddRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            risks: [],
            riskIDs: [],
            planID: this.props.idplan,

            sortingWay: 'desc',
            value: 'date'
        }

       // this.updateRiesgos = this.updateRiesgos.bind(this);
       // this.updateRiesgosBySearch = this.updateRiesgosBySearch.bind(this);
    }

    componentDidMount() {
        let options = {
            url: process.env.REACT_APP_API_URL + "/PlanManager/getRiskListByPlanNoRep",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.planID,
            }
        }

        axios(options).then(response => {
            this.setState({ risks: response.data })
        }).catch((error) => {
            console.error(error.message);
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/associateRiskToPlan`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.planID,
                'riskIDs': this.state.riskIDs
            }
        }

        axios(options)
            .then(response => {
              //  this.props.updateRiesgos("add-success");//<- XD
                //this.props.closeModal();
            }).catch(error => {
                toast.error("ID del riesgo ya se encuentra registrado en el sistema.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 10000
                });
            });

    }




    render() {

        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header closeButton>
                    Agregar Riesgos al Plaan
                </Modal.Header>
                <Modal.Body>
                    <Table>

                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Tipo General</th>
                                <th>Tipo por Área</th>
                                <th>Tipo Específico</th>
                                <th></th>
                            </tr>
                        </thead>

                        <Form onSubmit={this.handleSubmit}>
                            <FormGroup>

                                {this.state.risks.map((risk) => {
                                    return (

                                        <tr key={risk.id}>
                                            <td>{risk.id}</td>
                                            <td className="nameSlot">{risk.name}</td>
                                            <td>{risk.generalType}</td>
                                            <td>{risk.areaType}</td>
                                            <td>{risk.specType}</td>
                                            <td><input type="checkbox"
                                                aria-label="Seleccionar Riesgo"
                                                name="selectRisk"
                                                value={risk.id}></input>
                                            </td>
                                        </tr>
                                    )
                                })}
                            </FormGroup>

                            <Button className='btn-sfr' type="submit" id="submit-button-new-item" onClick={this.handleSubmit()}>
                                Guardar
                            </Button>
                        </Form>
                    </Table>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );

    };



};
export default AddRiskModal;