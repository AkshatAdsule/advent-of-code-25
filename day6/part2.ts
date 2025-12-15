import { stdin } from "node:process";
import { text } from "node:stream/consumers";

type Operation = "+" | "*";

interface ParsedInput {
	dataRows: string[];
	operations: Operation[];
	columnBoundaries: number[];
}

async function readStdin(): Promise<string> {
	return text(stdin);
}

function parseInput(input: string): ParsedInput {
	const lines = input.split("\n");
	const operatorRow = lines.at(-1) ?? "";
	const dataRows = lines.slice(0, -1);

	const operations = operatorRow.split(/\s+/).filter(Boolean) as Operation[];
	const columnBoundaries = findColumnBoundaries(operatorRow);

	return { dataRows, operations, columnBoundaries };
}

function findColumnBoundaries(operatorRow: string): number[] {
	const boundaries: number[] = [];

	for (let i = operatorRow.length - 1; i > 0; --i) {
		if (operatorRow[i] !== " ") {
			boundaries.unshift(i - 1);
		}
	}

	return boundaries;
}

function extractColumns(
	dataRows: string[],
	columnBoundaries: number[],
	columnCount: number,
): string[][] {
	const columns: string[][] = Array.from({ length: columnCount }, () => []);

	for (const row of dataRows) {
		let startIndex = 0;

		for (const [colIndex, boundary] of columnBoundaries.entries()) {
			columns[colIndex]?.push(row.substring(startIndex, boundary));
			startIndex = boundary + 1;
		}

		columns.at(-1)?.push(row.substring(startIndex));
	}

	return columns;
}

function transposeToVerticalNumbers(rows: string[]): number[] {
	const charGrid = rows.map((r) => [...r]);
	const transposed: string[] = [];

	for (const charRow of charGrid) {
		for (const [colIndex, char] of charRow.entries()) {
			transposed[colIndex] = (transposed[colIndex] ?? "") + char;
		}
	}

	return transposed.map((s) => parseInt(s.trim(), 10));
}

function applyOperation(numbers: number[], operation: Operation): number {
	if (operation === "+") {
		return numbers.reduce((sum, n) => sum + n, 0);
	}
	return numbers.reduce((product, n) => product * n, 1);
}

function calculateColumnResults(
	columns: string[][],
	operations: Operation[],
): number[] {
	return columns.map((column, index) => {
		const numbers = transposeToVerticalNumbers(column);
		const operation = operations[index] ?? "+";
		return applyOperation(numbers, operation);
	});
}

async function main() {
	const input = await readStdin();
	const { dataRows, operations, columnBoundaries } = parseInput(input);

	const columns = extractColumns(dataRows, columnBoundaries, operations.length);

	const results = calculateColumnResults(columns, operations);

	const total = results.reduce((sum, n) => sum + n, 0);
	console.log(`Part 2: ${total}`);
}

await main();
