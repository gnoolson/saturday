import timeout from "/static/js/timeout.js"

const options = [
    { type: "hex_string", str: "hex string" },
    { type: "string", str: "string" },
    { type: "int8", str: "int8" },
    { type: "int8", str: "uint8" },
    { type: "int16", str: "int16 be", le: false },
    { type: "int16", str: "int16 le", le: true },
    { type: "int16", str: "uint16 be", le: false },
    { type: "int16", str: "uint16 le", le: true },
    { type: "int32", str: "int32 be", le: false },
    { type: "int32", str: "int32 le", le: true },
    { type: "int32", str: "uint32 be", le: false },
    { type: "int32", str: "uint32 le", le: true },
    { type: "float", str: "float be", le: false },
    { type: "float", str: "float le", le: true },
    { type: "double", str: "double be", le: false },
    { type: "double", str: "double le", le: true },
]

export default {

    template: `

        <div class="split-button">
            <button @click="execute()" :disabled="waitExecutionResult">
                {{messages.button}}
            </button>
            <button @click="show = true" class="secondary-part secondary">☰</button>
        </div>

        <dialog :open="show?'true':null">
            <article>

                <header>
                    <button aria-label="Close" rel="prev" @click="hideDialog()"></button>
                    <span>
                        {{messages.title}}
                        <a href="/docs/script#execute" target="_blank"><img src="/static/img/info-square-fill.svg" /></a> 
                    </span>
                </header>

                <textarea id="mock_data"></textarea>


                <footer>

                    <div class="input-group">
                        <input 
                            v-model="payload.input"
                            type="text"
                            placeholder="Payload">

                        <select v-model="payload.selectedOption">
                            <option v-for="option in payloadOptions" :value="option">{{option.str}}</option>
                        </select>

                        <button @click="setPayload()">{{messages.setOutput}}</button>
                    </div>

                    <label>
                        <input v-model="logToOutput" type="checkbox" role="switch"/>
                        <span>{{messages.logToOutput}}</span>
                    </label>
                    <label>
                        <input v-model="logDebugEnabled" type="checkbox" role="switch"/>
                        <span>{{messages.logLogDebugEnabled}}</span>
                    </label>
                    <label>
                        <input v-model="outgoingMessageToOutput" type="checkbox" role="switch"/>
                        <span>{{messages.outgoingMessageToOutput}}</span>
                    </label>
                    <div style="mt-2">
                        <button style="width:100%;" v-if="!mockDataIsInvalid" @click="execute()"  :disabled="waitExecutionResult">{{messages.button}}</button>
                        <button style="width:100%;" v-if="mockDataIsInvalid" disabled>Mock Data is Invalid</button>
                    </div>
                </footer>

            </article>
        </dialog>
    `,

    props: ["output", "script", "messages"],

    mounted() {
    },

    data() {
        return {
            show: false,
            logToOutput: true,
            outgoingMessageToOutput: true,
            logDebugEnabled: true,
            mockDataIsInvalid: false,
            payloadOptions: options,
            waitExecutionResult: false,
            payload: {
                selectedOption: options[0],
                input: "ffffffff"
            },
            mockData: {
                args: {
                    key1: "value1",
                    key2: 3.14,
                    key3: {
                        key4: "value4"
                    },
                    source: "DEV"
                },
                client: {
                    clientId: "00000000-0000-0000-0000-000000000000",
                    incomingMessage: {
                        topic: "demo/house/room/1/radiator/1",
                        topicFilter: "demo/house/room/1/radiator/+",
                        qos: 0,
                        payload: "ffffffff"
                    }
                },
                dashboard: {
                    dashboardId: generateUUID(),
                    openDashboardId: generateUUID(),
                    incomingMessage: {
                        key1: "value1",
                        key2: 3.14,
                        key3: {
                            key4: "value4"
                        }
                    }
                }
            }
        }
    },

    methods: {

        init() {
            this.codeMirror = Vue.markRaw(CodeMirror.fromTextArea(document.getElementById("mock_data"), {
                lineNumbers: true,
                autoRefresh: true,
                mode: "javascript",
                extraKeys: {
                    "Shift-Tab": "indentLess"
                }
            }));

            this.codeMirror.setSize(null, "300px");

            this.codeMirror.getDoc().on("change", (instance, changeObj) => {
                const text = instance.getValue();
                try {
                    this.mockData = JSON.parse(text);
                    this.mockDataIsInvalid = false;
                } catch (e) {
                    this.mockDataIsInvalid = true;
                }
            });


            let mockDataJSON = JSON.stringify(this.mockData, null, 2);
            this.codeMirror.setValue(mockDataJSON);
        },

        hideDialog() {
            this.show = false;
        },

        setPayload() {
            let hexString;

            if (this.payload.selectedOption.type === "int8") {
                const buffer = new ArrayBuffer(1);
                const view = new DataView(buffer);
                view.setUint8(0, Number(this.payload.input), false);
                const bytes = new Uint8Array(buffer);
                hexString = toHex(bytes);
            } else if (this.payload.selectedOption.type === "int16") {
                const buffer = new ArrayBuffer(2);
                const view = new DataView(buffer);
                view.setUint16(0, Number(this.payload.input), this.payload.selectedOption.le);
                const bytes = new Uint16Array(buffer);
                hexString = toHex(bytes);
            } else if (this.payload.selectedOption.type === "int32") {
                const buffer = new ArrayBuffer(4);
                const view = new DataView(buffer);
                view.setUint32(0, Number(this.payload.input), this.payload.selectedOption.le);
                const bytes = new Uint32Array(buffer);
                hexString = toHex(bytes);
            } else if (this.payload.selectedOption.type === "float") {
                const buffer = new ArrayBuffer(4);
                const view = new DataView(buffer);
                view.setFloat32(0, Number(this.payload.input), this.payload.selectedOption.le);
                const bytes = new Uint32Array(buffer);
                hexString = toHex(bytes);
            } else if (this.payload.selectedOption.type === "double") {
                const buffer = new ArrayBuffer(8);
                const view = new DataView(buffer);
                view.setFloat64(0, Number(this.payload.input), this.payload.selectedOption.le);
                const bytes = new Float64Array(buffer);
                hexString = toHex(bytes);
            } else if (this.payload.selectedOption.type === "string") {
                hexString = toHex(new TextEncoder().encode(this.payload.input));
            } else if (this.payload.selectedOption.type === "hex_string") {
                hexString = this.payload.input + "";
            }

            this.mockData.messageBox.incomingMessage.payload = hexString;
            let mockDataJSON = JSON.stringify(this.mockData, null, 2);
            this.codeMirror.setValue(mockDataJSON);
        },

        execute() {

            this.waitExecutionResult = true;
            let dto = {
                projectId: this.script.projectId,
                scriptId: this.script.id,
                logToOutput: this.logToOutput,
                outgoingMessageToOutput: this.outgoingMessageToOutput,
                code: this.script.code,
                includedScripts: this.script.includedScripts,
                mockData: this.mockData,
                logDebugEnabled: this.logDebugEnabled
            };

            this.hideDialog();

            axios.post("/api/script/dev", dto).then((response) => {
                this.output.message = "-- " + getFormatedTime() +" Execution time: " + response.data.executionTime + "ms\n\r" + response.data.output;
                this.output.showMessage = true;
                this.waitExecutionResult = false;
            }, (error) => {
                this.output.message = "-- " + getFormatedTime() +" Error:\n\r" + error.response.data[0].message;
                this.output.showMessage = true;
                this.waitExecutionResult = false;
            });
        },

        mockDataJson() {
            return JSON.stringify(this.mockData, null, 2);
        },

        destroy() {
            if (this.codeMirror) {
                this.codeMirror.toTextArea();
                this.codeMirror = null;
            }
        }

    },

    computed: {



    },

    watch: {
        show(value) {
            if (value)
                timeout.exec("init", this.init, 10);
            else
                this.destroy()
        }
    }

}



function toHex(array) {
    const bytes = new Uint8Array(
        array.buffer,
        array.byteOffset,
        array.byteLength
    );

    return Array.from(bytes)
        .map(b => b.toString(16).padStart(2, '0'))
        .join('');
}

function getFormatedTime() {
    const now = new Date();

    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const ms = String(now.getMilliseconds()).padStart(3, '0');

    const formattedTime = `${hours}:${minutes}:${seconds}:${ms}`;
    return formattedTime;
}

function generateUUID() {
    var d = new Date().getTime();
    var d2 = ((typeof performance !== 'undefined') && performance.now && (performance.now()*1000)) || 0;
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16;
        if(d > 0){
            r = (d + r)%16 | 0;
            d = Math.floor(d/16);
        } else {
            r = (d2 + r)%16 | 0;
            d2 = Math.floor(d2/16);
        }
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}