const bottomPanelMinHeight = 20;
const topPanelMinHeight = 100;
let topPanelHeight;
let savedTopPanelHeight = null;
let isCurrentlyHidden = null;
let onHideCallback = null;
let onShowCallback = null;

function updatePanelsLayout(newTopHeight) {
    const topPanel = document.getElementById("topPanel");
    const resizer = document.getElementById("dragMe");
    const bottomPanel = document.getElementById("bottomPanel");
    const container = document.getElementById("main-container");

    const containerHeight = container.clientHeight;
    const maxTopPanelHeight = containerHeight - bottomPanelMinHeight;

    topPanelHeight = newTopHeight;

    topPanel.style.height = topPanelHeight + "px";
    resizer.style.top = topPanelHeight + "px";
    bottomPanel.style.top = topPanelHeight + "px";

    if (topPanelHeight >= maxTopPanelHeight) {
        if (isCurrentlyHidden !== true) {
            isCurrentlyHidden = true;
            if (typeof onHideCallback === "function") {
                onHideCallback();
            }
        }
    } else {
        if (isCurrentlyHidden !== false) {
            isCurrentlyHidden = false;
            if (typeof onShowCallback === "function") {
                onShowCallback();
            }
        }
    }
}

function initDragMe(onHide, onShow) {
    const resizer = document.getElementById("dragMe");
    const topPanel = document.getElementById("topPanel");
    const container = document.getElementById("main-container");

    onHideCallback = onHide;
    onShowCallback = onShow;

    let y = 0;
    let topHeight = 0;

    const containerHeight = container.clientHeight;
    const initialTopHeight = containerHeight - bottomPanelMinHeight;

    updatePanelsLayout(initialTopHeight);

    savedTopPanelHeight = containerHeight - (containerHeight * 0.10);

    const mouseDownHandler = function (e) {
        y = e.clientY;
        topHeight = topPanel.getBoundingClientRect().height;

        resizer.classList.add("active");
        container.classList.add("is-resizing");

        document.addEventListener("mousemove", mouseMoveHandler);
        document.addEventListener("mouseup", mouseUpHandler);
    };

    const mouseMoveHandler = function (e) {
        const dy = e.clientY - y;
        let calculatedHeight = topHeight + dy;

        const currentContainerHeight = container.clientHeight;

        if (calculatedHeight < topPanelMinHeight)
            calculatedHeight = topPanelMinHeight;

        if (calculatedHeight > currentContainerHeight - bottomPanelMinHeight)
            calculatedHeight = currentContainerHeight - bottomPanelMinHeight;

        updatePanelsLayout(calculatedHeight);

        savedTopPanelHeight = calculatedHeight;
    };

    const mouseUpHandler = function () {
        resizer.classList.remove("active");
        container.classList.remove("is-resizing");

        document.removeEventListener("mousemove", mouseMoveHandler);
        document.removeEventListener("mouseup", mouseUpHandler);
    };

    resizer.addEventListener("mousedown", mouseDownHandler);

    window.addEventListener("resize", () => {
        if (topPanelHeight >= container.clientHeight - bottomPanelMinHeight + 1) {
            updatePanelsLayout(container.clientHeight - bottomPanelMinHeight);
        } else {
            const currentTopHeight = topPanel.getBoundingClientRect().height;
            updatePanelsLayout(currentTopHeight);
        }
    });
}

function minimizeBottomPanel() {
    const container = document.getElementById("main-container");
    const containerHeight = container.clientHeight;

    savedTopPanelHeight = topPanelHeight;

    const targetTopHeight = containerHeight - bottomPanelMinHeight;
    updatePanelsLayout(targetTopHeight);
}

function restoreBottomPanel() {
    const container = document.getElementById("main-container");
    const containerHeight = container.clientHeight;

    const maxTopPanelHeight = containerHeight - bottomPanelMinHeight;
    const defaultFallbackHeight = containerHeight - (containerHeight * 0.10);

    if (topPanelHeight >= maxTopPanelHeight) {
        if (savedTopPanelHeight !== null && savedTopPanelHeight < defaultFallbackHeight) {
            updatePanelsLayout(savedTopPanelHeight);
        } else {
            updatePanelsLayout(defaultFallbackHeight);
        }
        savedTopPanelHeight = null;
    }
}

export {
    initDragMe,
    minimizeBottomPanel,
    restoreBottomPanel
}