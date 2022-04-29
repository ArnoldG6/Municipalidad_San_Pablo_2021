import React, { Component } from 'react';
import axios from 'axios';
import '../Plan.css'
import { Modal, Button, Form, OverlayTrigger, Tooltip, Stack } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import DatePicker from "react-datepicker";
import 'react-toastify/dist/ReactToastify.css';
import es from 'date-fns/locale/es';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

class AddIncidentModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            risks: [],
            validated: false,
            planID: "",
            startDate: new Date()
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.onChangeDatePicker = this.onChangeDatePicker.bind(this);
        this.setValidated = this.setValidated.bind(this);
        // this.getID = this.getID.bind(this);
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    onChangeDatePicker = e => {
        this.setState({ startDate: e })
    }

    handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: process.env.REACT_APP_SFR_API_URL + `/PlanManager/Insert/Incidence`,
                method: 'POST',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'name': event.target.name.value,
                    'description': event.target.description.value,
                    'entryDate': this.state.startDate.getTime(),
                    'affectation': event.target.affectation.value,
                    'cause': event.target.cause.value,
                    'risk': event.target.risk.value,
                    'planID': this.props.planID,
                    'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
                }
            }

            axios(options)
                .then(response => {
                    this.props.closeModal();
                    this.props.refreshPage();
                })
                .catch(error => {
                    var msj = "";
                    if (error.response) {
                        //Server responded with an error
                        switch (error.response.status) {
                            case 400:
                                msj = "Hubo un problema insertando la Incidencia solicitada.";
                                break;
                            case 401:
                                msj = "Este usuario no cuenta con permisos para insertar Incidencias a este Plan.";
                                break;
                            case 500:
                                msj = "El servidor ha encontrado un error desconocido.";
                                break;
                            default:
                                msj = "El servidor ha encontrado un error desconocido.";
                                break;
                        }
                    } else if (error.request) {
                        //Server did not respond
                        msj = "Hubo un error con la conexión al servidor."
                    } else {
                        //Something else went wrong
                        msj = "Error desconocido."
                    }
                    toast.error(msj, {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });
                })
        }
        this.setValidated(true);
    }

    setValidated(value) {
        this.setState({ validated: value });
    }

    render() {
        const DateButton = React.forwardRef(({ value, onClick }, ref) => (
            <Button variant="outline-primary" onClick={onClick} ref={ref}>
                {value}
            </Button>
        ));
        let render = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <Modal show={render} onHide={() => { this.setState({ value: "Evaluar, Dirigir y Monitorear" }); closeModal() }} >
                <Modal.Header closeButton>
                    Ingrese los datos de la nueva incidencia
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Nombre:</Form.Label>
                                <Form.Control
                                    name="name"
                                    id="name"
                                    type="text"
                                    placeholder="Nombre"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar nombre.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Fecha:</Form.Label><br />
                                <DatePicker
                                    selected={this.state.startDate}
                                    onChange={this.onChangeDatePicker}
                                    name="fecha"
                                    customInput={<DateButton />}
                                    locale={es}
                                    dateFormat="dd/MM/yyyy" />
                            </div>
                        </Form.Group>
                        <div className="form-group">
                            <label>Causa:</label>
                            <Form.Select name="cause" id="cause">
                                <option value="Error humano">Error humano</option>
                                <option value="Fallo del proceso">Fallo del proceso</option>
                                <option value="Desastres naturales">Desastres naturales</option>
                                <option value="Cambios climáticos">Cambios climáticos</option>
                                <option value="Activades Antrópicas">Activades Antrópicas</option>
                                <option value="Otros">Otros</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Riesgo Asociado:</label>
                            <Form.Select name="risk" id="risk" onChange={this.onChange}>
                                <option value={-1} >Sin riesgos</option>
                                {
                                    (this.props.risks === null) ?
                                        <option value={null} key="disabledRiskIncidence" disabled>Error cargando los riegos</option> :

                                        this.props.risks.map((risk) => {

                                            return <option value={risk.pkID} key={risk.name}>{risk.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Afectación:</label>
                                <OverlayTrigger
                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_RIESGOS_HELP_IMPACTO}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>
                            <input min="1" max="100" step="1" name="affectation" id="affectation" type="number" className="form-control number-input" placeholder="1%" required />
                        </div>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Descripción:</label>
                                <OverlayTrigger

                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_PLANES_HELP_DESC}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" style={{ height: '150px' }} required />
                        </div>
                        <div className='text-center'>
                            <Button className='btn-sfr' type="submit">
                                Guardar
                            </Button>
                        </div>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default AddIncidentModal;